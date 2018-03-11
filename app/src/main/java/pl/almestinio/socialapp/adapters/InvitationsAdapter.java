package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
import pl.almestinio.socialapp.http.friend.Friend_;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.menuInvitationsToFriendsView.InvitationsViewContracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/11/2018.
 */

public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.ViewHolder> {

    private InvitationsViewContracts.InvitationsViewPresenter invitationsViewPresenter;
    private List<Friend_> friendList;
    private Context context;
    private View view;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(10)
            .oval(false)
            .build();

    public InvitationsAdapter(List<Friend_> friendList, Context context, InvitationsViewContracts.InvitationsViewPresenter invitationsViewPresenter){
        this.friendList = friendList;
        notifyItemRangeChanged(0, friendList.size());
        this.context = context;
        this.invitationsViewPresenter = invitationsViewPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_not_confirmed_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Friend_ friends = friendList.get(position);

        getUserPic(friends, holder);
        getUserName(friends, holder);

        holder.imageViewFriendsUserPhoto.setOnClickListener(v -> invitationsViewPresenter.onUserImageViewClick(friends.getActionUserId()));
        holder.textViewFriendsUserName.setOnClickListener(v -> invitationsViewPresenter.onUserImageViewClick(friends.getActionUserId()));
        holder.buttonFriendsConfirm.setOnClickListener(v -> invitationsViewPresenter.onConfirmButtonClick(User.getUserId(), friends.getActionUserId()));
        holder.buttonFriendsDelete.setOnClickListener(v -> invitationsViewPresenter.onDeleteButtonClick(friends.getRelationshipId()));

    }


    private void getUserPic(Friend_ friends, final ViewHolder holder){
        RestClient.getClient().requestUserPhoto(friends.getActionUserId()).enqueue(new Callback<UserPhoto>() {
            @Override
            public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                for(UsersPic userPic : response.body().getUsersPic()){
                    if(!userPic.getUserPic().getImage().isEmpty()){
                        Picasso.with(context).load(userPic.getUserPic().getImage()).fit().centerCrop().transform(transformation).placeholder(R.drawable.logo).into(holder.imageViewFriendsUserPhoto);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserPhoto> call, Throwable t) {
            }
        });
    }

    private void getUserName(Friend_ friends, final ViewHolder holder){
        try {
            RestClient.getClient().requestUser(friends.getActionUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    holder.textViewFriendsUserName.setText(test);
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
    public int getItemCount() {
        return friendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewFriendsUserPhoto)
        ImageView imageViewFriendsUserPhoto;
        @BindView(R.id.textViewFriendsUserName)
        TextView textViewFriendsUserName;
        @BindView(R.id.buttonFriendsConfirm)
        Button buttonFriendsConfirm;
        @BindView(R.id.buttonFriendsDelete)
        Button buttonFriendsDelete;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
