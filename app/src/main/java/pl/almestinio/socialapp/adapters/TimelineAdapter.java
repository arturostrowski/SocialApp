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
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.ui.menuTimelineView.TimelineViewContracts;
import pl.almestinio.socialapp.ui.menuTimelineView.TimelineViewPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> implements TimelineViewContracts.TimelineView {

    TimelineViewContracts.TimelineViewPresenter timelineViewPresenter;

    List<Post_> postsList;
    Context context;
    View view;

    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();


    public TimelineAdapter(List<Post_> postsList, Context context){
        this.postsList = postsList;
        notifyItemRangeChanged(0, postsList.size());
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        timelineViewPresenter = new TimelineViewPresenter(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post_ post = postsList.get(position);

        getUserName(post, holder);

        holder.textViewFullName.setOnClickListener(v -> timelineViewPresenter.onNameTextViewClick(holder.textViewFullName.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    private void getUserName(Post_ post, final  ViewHolder holder){
        try {
            RestClient.getClient().requestUser(post.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    holder.textViewFullName.setText(test);
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
