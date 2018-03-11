package pl.almestinio.socialapp.ui.profileView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.ProfileGalleryAdapter;
import pl.almestinio.socialapp.adapters.TimelineAdapter;
import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RequestsService;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.UploadObject;
import pl.almestinio.socialapp.http.friend.Friend;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.http.post.Post;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphotocover.UserPhotoCover;
import pl.almestinio.socialapp.http.userphotocover.UsersPic;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.commentsView.CommentsActivity;
import pl.almestinio.socialapp.ui.fullScreenPictureView.FullScreenPictureActivity;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class ProfileActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, SwipeRefreshLayout.OnRefreshListener, ProfileViewContracts.ProfileView {

    @BindView(R.id.textViewProfileName)
    TextView textViewProfileName;
    @BindView(R.id.imageViewProfilePhoto)
    ImageView imageViewProfilePhoto;
    @BindView(R.id.imageViewProfilePhotoCover)
    ImageView imageViewProfilePhotoCover;
    @BindView(R.id.buttonEditUserPhoto)
    Button buttonEditUserPhoto;
    @BindView(R.id.buttonEditUserPhotoCover)
    Button buttonEditUserPhotoCover;

    @BindView(R.id.viewProfileOptions)
    View viewProfileOptions;
    @BindView(R.id.imageViewFriends)
    ImageView imageViewFriends;
    @BindView(R.id.textViewFriends)
    TextView textViewFriends;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(2)
            .build();

    private ProfileViewContracts.ProfileViewPresenter profileViewPresenter;

    private TimelineAdapter profilePostsAdapter;
    private ProfileGalleryAdapter profileGalleryAdapter;
    private RecyclerView recyclerViewPosts;
    private RecyclerView recyclerViewGallery;
    private List<Post_> galleryList = new ArrayList<Post_>();
    private List<Post_> postslist = new ArrayList<Post_>();

    private Bundle bundle;
    private String bundleStringUserId;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String SERVER_PATH = "https://almestinio.pl/";
    public static final int PICK_IMAGE = 100;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;
    private int id;

    private String fileName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        bundleStringUserId = bundle.getString("userid");
        profileViewPresenter = new ProfileViewPresenter(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_profile);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerViewPosts = (RecyclerView) findViewById(R.id.recyclerViewProfilePosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPosts.setLayoutManager(layoutManager);

        recyclerViewGallery = (RecyclerView) findViewById(R.id.recyclerViewGallery);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getApplicationContext(),2);
        recyclerViewGallery.setLayoutManager(layoutManager2);


        profileViewPresenter.loadUserProfile(bundleStringUserId);

        buttonEditUserPhoto.setOnClickListener(v -> profileViewPresenter.onUserChangePhotoClick(1));
        buttonEditUserPhotoCover.setOnClickListener(v -> profileViewPresenter.onUserChangePhotoClick(2));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserChangePicture(String userId) {
        if(userId.equals(User.getUserId())){
            buttonEditUserPhoto.setVisibility(View.VISIBLE);
            buttonEditUserPhotoCover.setVisibility(View.VISIBLE);
        }
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
    public void showUserFriendOption(String userId, String userTwoId) {
        String userOne;
        String userTwo;

        if(Integer.parseInt(userId)<Integer.parseInt(userTwoId)){
            userOne = userId;
            userTwo = userTwoId;
        }else{
            userOne = userTwoId;
            userTwo = userId;
        }
        Log.e("USERONE", userOne);
        Log.e("USERTWO", userTwo);
        if(userOne.equals(userTwo)) {
            viewProfileOptions.setVisibility(View.GONE);
            imageViewFriends.setVisibility(View.GONE);
            textViewFriends.setVisibility(View.GONE);
        }else{
            try{

                RestClient.getClient().requestFriend(userOne, userTwo).enqueue(new Callback<UserFriend>() {
                    @Override
                    public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

                        if(response.body().getFriends().isEmpty()){
                            imageViewFriends.setBackgroundResource(R.drawable.ic_profile_friend_add);
                            textViewFriends.setText("Dodaj do znajomych");
                            imageViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, userOne, userTwo, User.getUserId()));
                            textViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, userOne, userTwo, User.getUserId()));
                        }

                        for(Friend friend : response.body().getFriends()){
                            if(friend.getFriend().getStatus().equals("0")){
                                if(friend.getFriend().getActionUserId().equals(User.getUserId())){
                                    imageViewFriends.setBackgroundResource(R.drawable.ic_profile_friend);
                                    textViewFriends.setText("Oczekiwanie");
                                    imageViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, friend.getFriend().getRelationshipId(), userOne));
                                    textViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, friend.getFriend().getRelationshipId(), userOne));
                                }else{
                                    imageViewFriends.setBackgroundResource(R.drawable.ic_profile_friend);
                                    textViewFriends.setText("Akceptuj");
                                    imageViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(true, userOne, userTwo));
                                    textViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(true, userOne, userTwo));
                                }
                            }else if(friend.getFriend().getStatus().equals("1")){
                                imageViewFriends.setBackgroundResource(R.drawable.ic_profile_friend);
                                textViewFriends.setText("Friends");
                                imageViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, friend.getFriend().getRelationshipId(), userTwo));
                                textViewFriends.setOnClickListener(v -> profileViewPresenter.onFriendsImageOrTextViewClick(false, friend.getFriend().getRelationshipId(), userTwo));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserFriend> call, Throwable t) {

                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
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
    public void showUserPosts(String userId) {
        postslist.clear();
        try{
            RestClient.getClient().requestPostsUser(userId).enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    for(Post posts : response.body().getPosts()){
                        if(!posts.getPost().getPostPic().isEmpty()){
                            postslist.add(new Post_(posts.getPost().getPostId().toString(), posts.getPost().getUserId().toString(), posts.getPost().getPostTxt().toString(), posts.getPost().getPostPic().toString(), posts.getPost().getPostTime().toString(), posts.getPost().getPriority().toString()));
                            Log.i("POST", "DODANO");
                        }
                        try{
                            profilePostsAdapter.notifyItemRangeChanged(0,profilePostsAdapter.getItemCount());
                            profilePostsAdapter.notifyDataSetChanged();
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
    public void likePost(String postId) {
        try{
            RestClient.getClient().likePost(postId, User.getUserId()).enqueue(new Callback<Pojodemo>() {
                @Override
                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                }
                @Override
                public void onFailure(Call<Pojodemo> call, Throwable t) {
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        profilePostsAdapter.notifyDataSetChanged();
    }

    @Override
    public void unlikePost(String postId) {
        try{
            RestClient.getClient().deleteLike(postId, User.getUserId()).enqueue(new Callback<Pojodemo>() {
                @Override
                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                    if(response.body().getSuccess()){
                        Log.e("Usunieto", ":D");
                    }else{
                        Log.e("aaaa", response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<Pojodemo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        profilePostsAdapter.notifyDataSetChanged();
    }

    @Override
    public void startProfileActivity(String userId) {
        startActivity(new Intent(this, ProfileActivity.class).putExtra("userid", userId));
    }

    @Override
    public void startCommentsActivity(String postId) {
        startActivity(new Intent(this, CommentsActivity.class).putExtra("postid", postId));
    }

    @Override
    public void showDeletePostAlert(String postId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Usun post")
                .setMessage("Czy chcesz usunac ten post?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            RestClient.getClient().deletePost(postId).enqueue(new Callback<Pojodemo>() {
                                @Override
                                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {

                                }

                                @Override
                                public void onFailure(Call<Pojodemo> call, Throwable t) {

                                }
                            });
                            Log.i("USUN", "XDDD");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        activeNetwork = connectivityManager.getActiveNetworkInfo();
//                        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//
//                        timelineViewPresenter.loadPosts(isConnected);
//                        setAdapterAndGetRecyclerView();
//                        timelineAdapter.notifyDataSetChanged();

                        profileViewPresenter.loadUserProfile(bundleStringUserId);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }

    @Override
    public void setAdapterAndGetRecyclerView() {
        profileGalleryAdapter = new ProfileGalleryAdapter(galleryList, this, profileViewPresenter);
        recyclerViewGallery.setAdapter(profileGalleryAdapter);
        recyclerViewGallery.setNestedScrollingEnabled(false);
        recyclerViewGallery.invalidate();

        profilePostsAdapter = new TimelineAdapter(postslist, this, profileViewPresenter, 2);
        recyclerViewPosts.setAdapter(profilePostsAdapter);
        recyclerViewPosts.setNestedScrollingEnabled(false);
        recyclerViewPosts.invalidate();
    }

    @Override
    public void addFriend(String userId, String userTwoId, String actionUserId) {
        try{
            RestClient.getClient().addFriend(userId, userTwoId, actionUserId).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

                }

                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {

                }
            });

            profileViewPresenter.loadUserProfile(bundleStringUserId);

//            finish();
//            startActivity(getIntent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void acceptFriend(String userId, String userTwoId) {
        try{
            RestClient.getClient().acceptFriend(userId, userTwoId).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

                }
                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {

                }
            });
            profileViewPresenter.loadUserProfile(bundleStringUserId);
//            finish();
//            startActivity(getIntent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeFriend(String userId) {
        try{
            RestClient.getClient().deleteFriend(userId).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

                }

                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {

                }
            });
            profileViewPresenter.loadUserProfile(bundleStringUserId);
//            finish();
//            startActivity(getIntent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void startFullScreenPicture(String imageUrl) {
        startActivity(new Intent(this, FullScreenPictureActivity.class).putExtra("imageurl", imageUrl));
    }

    @Override
    public void changePhoto(int id) {
        this.id = id;
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ProfileActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, ProfileActivity.this);
                File file = new File(filePath);
                Log.e("x", "Filename " + "user_id="+User.getUserId()+"_"+file.getName());
                fileName = "user_id="+User.getUserId()+"_"+file.getName();
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "user_id="+User.getUserId()+"_"+file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), "user_id="+User.getUserId()+"_"+file.getName());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestsService uploadImage = retrofit.create(RequestsService.class);
                Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        if(id==1){
                            try{
                                Picasso.with(getApplicationContext()).load("https://almestinio.pl/phpimage/"+fileName).fit().centerCrop().transform(transformation).into(imageViewProfilePhoto);
                                try{
                                    RestClient.getClient().changeUserPhoto(User.getUserId(), fileName).enqueue(new Callback<UserPhoto>() {
                                        @Override
                                        public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<UserPhoto> call, Throwable t) {
                                        }
                                    });

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else if(id==2){
                            try{
                                Picasso.with(getApplicationContext()).load("https://almestinio.pl/phpimage/"+fileName).fit().centerCrop().into(imageViewProfilePhotoCover);
                                try{
                                    RestClient.getClient().changeUserPhotoCover(User.getUserId(), fileName).enqueue(new Callback<UserPhoto>() {
                                        @Override
                                        public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                                        }
                                        @Override
                                        public void onFailure(Call<UserPhoto> call, Throwable t) {
                                        }
                                    });

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Log.e("xd", "Error " + t.getMessage());
                    }
                });

            }else{
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, ProfileActivity.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "user_id="+User.getUserId()+"_"+file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), "user_id="+User.getUserId()+"_"+file.getName());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestsService uploadImage = retrofit.create(RequestsService.class);
            Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                }
                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    Log.e("xdddd", "Error " + t.getMessage());
                }
            });

        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("dd", "Permission has been denied");
    }

    @Override
    public void onRefresh() {
        profileViewPresenter.loadUserProfile(bundleStringUserId);
        swipeRefreshLayout.setRefreshing(false);
    }
}
