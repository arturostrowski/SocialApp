package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
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
import pl.almestinio.socialapp.http.user.User_;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.ui.searchFriendsView.SearchFriendsViewContracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class SearchFriendsAdapter extends RecyclerView.Adapter<SearchFriendsAdapter.ViewHolder> {

    private SearchFriendsViewContracts.FriendsViewPresenter friendsViewPresenter;

    private List<User_> usersList;
    private Context context;
    private View view;

    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(10)
            .oval(false)
            .build();

    public SearchFriendsAdapter(List<User_> usersList, Context context, SearchFriendsViewContracts.FriendsViewPresenter friendsViewPresenter){
        this.usersList = usersList;
        notifyItemRangeChanged(0, usersList.size());
        this.context = context;
        this.friendsViewPresenter = friendsViewPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchfriends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User_ users = usersList.get(position);
        getUserPic(users, holder);
        holder.textViewSearchFriend.setText(users.getName());

        holder.constraintFriends.setOnClickListener(v -> friendsViewPresenter.onClickUser(users.getUserId()));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    private void getUserPic(User_ user, final SearchFriendsAdapter.ViewHolder holder){
        RestClient.getClient().requestUserPhoto(user.getUserId()).enqueue(new Callback<UserPhoto>() {
            @Override
            public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                for(UsersPic userPic : response.body().getUsersPic()){
                    if(!userPic.getUserPic().getImage().isEmpty()){
                        Picasso.with(context).load(userPic.getUserPic().getImage()).fit().centerCrop().transform(transformation).placeholder(R.drawable.logo).into(holder.imageViewSearchFriend);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserPhoto> call, Throwable t) {
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewSearchFriend)
        TextView textViewSearchFriend;
        @BindView(R.id.imageViewSearchFriend)
        ImageView imageViewSearchFriend;
        @BindView(R.id.constraintFriends)
        ConstraintLayout constraintFriends;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
