package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.comment.Comments;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.poststatus.Post;
import pl.almestinio.socialapp.http.poststatus.PostStatus;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.menuTimelineView.TimelineViewContracts;
import pl.almestinio.socialapp.ui.profileView.ProfileViewContracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private TimelineViewContracts.TimelineViewPresenter timelineViewPresenter;
    private ProfileViewContracts.ProfileViewPresenter profileViewPresenter;
    private int id = 1;

    private List<Post_> postsList;
    private Context context;
    private View view;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();


    public TimelineAdapter(List<Post_> postsList, Context context, TimelineViewContracts.TimelineViewPresenter timelineViewPresenter){
        this.postsList = postsList;
        notifyItemRangeChanged(0, postsList.size());
        this.context = context;
        this.timelineViewPresenter = timelineViewPresenter;
    }

    public TimelineAdapter(List<Post_> postsList, Context context, ProfileViewContracts.ProfileViewPresenter profileViewPresenter, int id){
        this.postsList = postsList;
        notifyItemRangeChanged(0, postsList.size());
        this.context = context;
        this.profileViewPresenter = profileViewPresenter;
        this.id = id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post_ post = postsList.get(position);
        holder.buttonLike.setTag("");
        holder.buttonLike.setTextColor(Color.parseColor("#AAAAAA"));
        getUserName(post, holder);
        getUserPic(post, holder);
        getCommentImage(post, holder);
        getLikeStatus(post, holder);
        getCommentStatus(post, holder);

        holder.textViewDate.setText(post.getPostTime());
        holder.textViewText.setText(post.getPostTxt());

        try{
            if(id==2){
                holder.textViewFullName.setOnClickListener(v -> profileViewPresenter.onNameTextViewClick(post.getUserId()));
                holder.imageViewUserProfile.setOnClickListener(v -> profileViewPresenter.onUserImageViewClick(post.getUserId()));
                holder.imageViewCommentPhoto.setOnClickListener(v -> profileViewPresenter.onImageViewClick(post.getPostPic()));
                holder.buttonLike.setOnClickListener(v -> profileViewPresenter.onLikeButtonClick(post.getPostId(), holder.buttonLike.getTag().equals("clicked")));
                holder.buttonComment.setOnClickListener(v -> profileViewPresenter.onCommentButtonClick(post.getPostId()));
                holder.textViewCountComments.setOnClickListener(v -> profileViewPresenter.onCommentButtonClick(post.getPostId()));
                holder.imageViewDeletePost.setOnClickListener(v -> profileViewPresenter.onDeleteImageViewClick(post.getPostId()));
            }else{
                holder.textViewFullName.setOnClickListener(v -> timelineViewPresenter.onNameTextViewClick(post.getUserId()));
                holder.imageViewUserProfile.setOnClickListener(v -> timelineViewPresenter.onUserImageViewClick(post.getUserId()));
                holder.imageViewCommentPhoto.setOnClickListener(v -> timelineViewPresenter.onImageViewClick(post.getPostPic()));
                holder.buttonLike.setOnClickListener(v -> timelineViewPresenter.onLikeButtonClick(post.getPostId(), holder.buttonLike.getTag().equals("clicked")));
                holder.buttonComment.setOnClickListener(v -> timelineViewPresenter.onCommentButtonClick(post.getPostId()));
                holder.textViewCountComments.setOnClickListener(v -> timelineViewPresenter.onCommentButtonClick(post.getPostId()));
                holder.imageViewDeletePost.setOnClickListener(v -> timelineViewPresenter.onDeleteImageViewClick(post.getPostId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    private void getUserName(Post_ post, final ViewHolder holder){
        try {
            RestClient.getClient().requestUser(post.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    holder.textViewFullName.setText(test);
                    String deletePost = response.body().getUsers().get(0).getUser().getUserId();
                    if(deletePost.equals(User.getUserId())){
                        holder.imageViewDeletePost.setVisibility(View.VISIBLE);
                    }else{
                        holder.imageViewDeletePost.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getUserPic(Post_ post, final ViewHolder holder){
        RestClient.getClient().requestUserPhoto(post.getUserId()).enqueue(new Callback<UserPhoto>() {
            @Override
            public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                for(UsersPic userPic : response.body().getUsersPic()){
                    if(!userPic.getUserPic().getImage().isEmpty()){
                        Picasso.with(context).load(userPic.getUserPic().getImage()).fit().centerCrop().transform(transformation).placeholder(R.drawable.logo).into(holder.imageViewUserProfile);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserPhoto> call, Throwable t) {}
        });
    }

    private void getCommentImage(Post_ post, final ViewHolder holder){
        try{
            holder.imageViewCommentPhoto.setVisibility(View.VISIBLE);
            Log.e("pic", post.getPostPic());
            Picasso.with(context).load(post.getPostPic()).fit().centerCrop().into(holder.imageViewCommentPhoto);
        }catch (Exception e){
            holder.imageViewCommentPhoto.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void getLikeStatus(Post_ post, final ViewHolder holder){
        try {
            RestClient.getClient().requestStatus(post.getPostId()).enqueue(new Callback<PostStatus>() {
                @Override
                public void onResponse(Call<PostStatus> call, Response<PostStatus> response) {
                    int size = response.body().getPosts().size();
                    Log.e("size", ""+size);
                    if(size==0){
                        holder.textViewCountLikes.setVisibility(View.INVISIBLE);
                        holder.imageViewCountLikes.setVisibility(View.INVISIBLE);
                    }else{
                        holder.textViewCountLikes.setVisibility(View.VISIBLE);
                        holder.textViewCountLikes.setText(String.valueOf(size));
                        holder.imageViewCountLikes.setVisibility(View.VISIBLE);
                    }

                    for(Post post : response.body().getPosts()){
                        if(post.getPost().getUserId().equals(User.getUserId())){
                            holder.buttonLike.setTag("clicked");
                            holder.buttonLike.setTextColor(Color.parseColor("#FF4081"));
                            break;
                        }else{
                            holder.buttonLike.setTag("");
                            holder.buttonLike.setTextColor(Color.parseColor("#AAAAAA"));
                        }
                    }

                }

                @Override
                public void onFailure(Call<PostStatus> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCommentStatus(Post_ post, final ViewHolder holder){
        try {
            RestClient.getClient().requestComments(post.getPostId()).enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    int size = response.body().getPosts().size();
                    Log.e("size", ""+size);
                    if(size==0){
                        holder.textViewCountComments.setVisibility(View.GONE);
                    }else{
                        holder.textViewCountComments.setVisibility(View.VISIBLE);
                        holder.textViewCountComments.setText(String.valueOf("Comments: "+size));
                    }
                }

                @Override
                public void onFailure(Call<Comments> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewUserProfilePhoto) ImageView imageViewUserProfile;
        @BindView(R.id.textViewPostFullName) TextView textViewFullName;
        @BindView(R.id.textViewPostDate) TextView textViewDate;
        @BindView(R.id.textViewIsOnline) TextView textViewIsOnline;
        @BindView(R.id.imageViewDeletePost) ImageView imageViewDeletePost;
        @BindView(R.id.textViewPostText) TextView textViewText;
        @BindView(R.id.imageViewCommentPhoto) ImageView imageViewCommentPhoto;
        @BindView(R.id.textViewCountLikes) TextView textViewCountLikes;
        @BindView(R.id.textViewCountComments) TextView textViewCountComments;
        @BindView(R.id.imageViewCountLikes) ImageView imageViewCountLikes;
        @BindView(R.id.buttonLike) Button buttonLike;
        @BindView(R.id.buttonComment) Button buttonComment;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
