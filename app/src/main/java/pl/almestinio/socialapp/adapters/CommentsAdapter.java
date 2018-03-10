package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pl.almestinio.socialapp.http.comment.Post_;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.commentsView.CommentsViewContracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/8/2018.
 */


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private CommentsViewContracts.CommentsViewPresenter commentsViewPresenter;

    private List<Post_> commentsList;
    private Context context;
    private View view;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(10)
            .oval(false)
            .build();

    public CommentsAdapter(List<Post_> commentsList, Context context, CommentsViewContracts.CommentsViewPresenter commentsViewPresenter) {
        this.commentsList = commentsList;
        notifyItemRangeChanged(0, commentsList.size());
        notifyDataSetChanged();
        this.context = context;
        this.commentsViewPresenter = commentsViewPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post_ post = commentsList.get(position);

        getUserPic(post, holder);
        getUserName(post, holder);

        holder.textViewCommentText.setText(post.getComment());

        holder.textViewCommentUserName.setOnClickListener(v -> commentsViewPresenter.onNameTextViewClick(post.getUserId()));
        holder.imageViewCommentUserPhoto.setOnClickListener(v -> commentsViewPresenter.onPhotoImageViewClick(post.getUserId()));
        holder.imageViewDeleteComment.setOnClickListener(v -> commentsViewPresenter.onDeleteImageViewClick(post.getCommentId(), post.getPostId()));

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    private void getUserName(Post_ post, final ViewHolder holder){
        try {
            RestClient.getClient().requestUser(post.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    holder.textViewCommentUserName.setText(test);
                    String deleteComment = response.body().getUsers().get(0).getUser().getUserId();

                    if(deleteComment.equals(User.getUserId())){
                        holder.imageViewDeleteComment.setVisibility(View.VISIBLE);
                    }else{
                        holder.imageViewDeleteComment.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
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
                        Picasso.with(context).load(userPic.getUserPic().getImage()).fit().centerCrop().transform(transformation).placeholder(R.drawable.logo).into(holder.imageViewCommentUserPhoto);
                    }

                }

            }

            @Override
            public void onFailure(Call<UserPhoto> call, Throwable t) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewCommentUserPhoto) ImageView imageViewCommentUserPhoto;
        @BindView(R.id.textViewCommentUserName) TextView textViewCommentUserName;
        @BindView(R.id.textViewCommentText) TextView textViewCommentText;
        @BindView(R.id.imageViewDeleteComment) ImageView imageViewDeleteComment;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
