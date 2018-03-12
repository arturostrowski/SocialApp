package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.ui.profileView.ProfileViewContracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/12/2018.
 */

public class ProfileFriendsAdapter extends RecyclerView.Adapter<ProfileFriendsAdapter.ViewHolder> {

    private ProfileViewContracts.ProfileViewPresenter profileViewPresenter;
    private List<Post_> friendsList;
    private Context context;
    private View view;


    public ProfileFriendsAdapter(List<Post_> friendsList, Context context, ProfileViewContracts.ProfileViewPresenter profileViewPresenter){
        this.friendsList = friendsList;
        notifyItemRangeChanged(0, friendsList.size());
        this.context = context;
        this.profileViewPresenter = profileViewPresenter;
    }

    @Override
    public ProfileFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false);
        return new ProfileFriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProfileFriendsAdapter.ViewHolder holder, final int position) {
        final Post_ post = friendsList.get(position);

        try{
            Picasso.with(context).load(post.getPostPic()).fit().centerCrop().into(holder.imageViewProfileFriends);
        }catch (Exception e){
            e.printStackTrace();
        }

        getUserName(post, holder);
        getUserPic(post, holder);

        holder.imageViewProfileFriends.setOnClickListener(v -> profileViewPresenter.onUserImageViewClick(post.getUserId()));

    }

    private void getUserPic(Post_ post, ProfileFriendsAdapter.ViewHolder holder){
        RestClient.getClient().requestUserPhoto(post.getUserId()).enqueue(new Callback<UserPhoto>() {
            @Override
            public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                for(UsersPic userPic : response.body().getUsersPic()){
                    if(!userPic.getUserPic().getImage().isEmpty()){
                        Picasso.with(context).load(userPic.getUserPic().getImage()).fit().centerCrop().placeholder(R.drawable.logo).into(holder.imageViewProfileFriends);
                    }

                }
            }

            @Override
            public void onFailure(Call<UserPhoto> call, Throwable t) {

            }
        });
    }

    private void getUserName(Post_ post, final ViewHolder holder){
        try {
            RestClient.getClient().requestUser(post.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    for(User user : response.body().getUsers()){
                        try{
                            holder.textViewProfileFriends.setText(user.getUser().getName());
                        }catch (Exception e){
                        }
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewProfileFriends)
        ImageView imageViewProfileFriends;
        @BindView(R.id.textViewProfileFriends)
        TextView textViewProfileFriends;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}