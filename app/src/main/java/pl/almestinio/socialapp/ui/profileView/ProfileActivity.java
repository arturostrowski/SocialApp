package pl.almestinio.socialapp.ui.profileView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.ProfileGalleryAdapter;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.post.Post;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphotocover.UserPhotoCover;
import pl.almestinio.socialapp.http.userphotocover.UsersPic;
import pl.almestinio.socialapp.ui.fullScreenPictureView.FullScreenPictureActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class ProfileActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ProfileViewContracts.ProfileView {

    @BindView(R.id.textViewProfileName)
    TextView textViewProfileName;
    @BindView(R.id.imageViewProfilePhoto)
    ImageView imageViewProfilePhoto;
    @BindView(R.id.imageViewProfilePhotoCover)
    ImageView imageViewProfilePhotoCover;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(2)
            .build();

    private ProfileViewContracts.ProfileViewPresenter profileViewPresenter;

    private ProfileGalleryAdapter profileGalleryAdapter;
    private RecyclerView recyclerViewGallery;
    private List<Post_> galleryList = new ArrayList<Post_>();

    private Bundle bundle;
    private String bundleStringUserId;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        bundleStringUserId = bundle.getString("userid");
        profileViewPresenter = new ProfileViewPresenter(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_profile);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerViewGallery = (RecyclerView) findViewById(R.id.recyclerViewGallery);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getApplicationContext(),2);
        recyclerViewGallery.setLayoutManager(layoutManager2);

        profileViewPresenter.loadUserProfile(bundleStringUserId);

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserPhoto(String userId) {
        try{
            RestClient.getClient().requestUserPhoto(userId).enqueue(new Callback<UserPhoto>() {
                @Override
                public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                    for(final pl.almestinio.socialapp.http.userphoto.UsersPic userPic : response.body().getUsersPic()) {
                        if (!userPic.getUserPic().getImage().isEmpty()) {
                            Picasso.with(getBaseContext()).load(userPic.getUserPic().getImage()).placeholder(R.color.colorGrey).fit().centerCrop().transform(transformation).into(imageViewProfilePhoto);
                            imageViewProfilePhoto.setOnClickListener(v -> profileViewPresenter.onUserPhotoClick(userPic.getUserPic().getImage()));
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserPhoto> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUserPhotoCover(String userId) {
        try{
            RestClient.getClient().requestUserPhotoCover(userId).enqueue(new Callback<UserPhotoCover>() {
                @Override
                public void onResponse(Call<UserPhotoCover> call, Response<UserPhotoCover> response) {
                    for(final UsersPic userPic : response.body().getUsersPic()){
                        Log.e("userpic", userPic.getUserPic().getImage());
                        if(!userPic.getUserPic().getImage().isEmpty()){
                            Picasso.with(getBaseContext()).load(userPic.getUserPic().getImage()).placeholder(R.color.colorGrey).fit().centerCrop().into(imageViewProfilePhotoCover);
                            imageViewProfilePhotoCover.setOnClickListener(v -> profileViewPresenter.onUserPhotoClick(userPic.getUserPic().getImage()));
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserPhotoCover> call, Throwable t) {
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUserName(String userId) {
        try {
            RestClient.getClient().requestUser(userId).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    textViewProfileName.setText(test);
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showUserGallery(String userId) {
        galleryList.clear();
        try{
            RestClient.getClient().requestPostsUser(userId).enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    for(Post posts : response.body().getPosts()){
                        if(!posts.getPost().getPostPic().isEmpty()){
                            galleryList.add(new Post_(posts.getPost().getPostId().toString(), posts.getPost().getUserId().toString(), posts.getPost().getPostTxt().toString(), posts.getPost().getPostPic().toString(), posts.getPost().getPostTime().toString(), posts.getPost().getPriority().toString()));
                        }
                        try{
                            profileGalleryAdapter.notifyItemRangeChanged(0, profileGalleryAdapter.getItemCount());
                            profileGalleryAdapter.notifyDataSetChanged();
                        }catch (Exception e){}
                    }
                }
                @Override
                public void onFailure(Call<Posts> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapterAndGetRecyclerView() {
        profileGalleryAdapter = new ProfileGalleryAdapter(galleryList, this, profileViewPresenter);
        recyclerViewGallery.setAdapter(profileGalleryAdapter);
        recyclerViewGallery.setNestedScrollingEnabled(false);
        recyclerViewGallery.invalidate();
    }

    @Override
    public void startFullScreenPicture(String imageUrl) {
        startActivity(new Intent(this, FullScreenPictureActivity.class).putExtra("imageurl", imageUrl));
    }


    @Override
    public void onRefresh() {
        profileViewPresenter.loadUserProfile(bundleStringUserId);
        swipeRefreshLayout.setRefreshing(false);
    }
}
