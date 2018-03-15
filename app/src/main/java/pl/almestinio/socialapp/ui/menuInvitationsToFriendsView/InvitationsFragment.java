package pl.almestinio.socialapp.ui.menuInvitationsToFriendsView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.InvitationsAdapter;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.friend.Friend;
import pl.almestinio.socialapp.http.friend.Friend_;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/11/2018.
 */

public class InvitationsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, InvitationsViewContracts.InvitationsView {


    private InvitationsAdapter invitationsAdapter;
    private List<Friend_> friendList = new ArrayList<Friend_>();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView recyclerView;

    private InvitationsViewContracts.InvitationsViewPresenter invitationsViewPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitations, container, false);
        setHasOptionsMenu(true);

        if(!friendList.isEmpty()){
            friendList.clear();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_invitations);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewInvitations);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        invitationsViewPresenter = new InvitationsViewPresenter(this);

        invitationsViewPresenter.getNotAcceptedUsers(User.getUserId());

        return view;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotAcceptedUsers(List<Friend> userFriendList) {
        friendList.clear();
        try{
            for(Friend friend : userFriendList){
                friendList.add(new Friend_(friend.getFriend().getRelationshipId(), friend.getFriend().getUserOneId(), friend.getFriend().getUserTwoId(), friend.getFriend().getStatus(), friend.getFriend().getActionUserId()));
            }
            invitationsAdapter.notifyItemRangeChanged(0, friendList.size());
            invitationsAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void acceptUser(String userId, String userTwoId) {
        String userOne;
        String userTwo;
        if(Integer.parseInt(userId)<Integer.parseInt(userTwoId)){
            userOne = userId;
            userTwo = userTwoId;
        }else{
            userOne = userTwoId;
            userTwo = userId;
        }
        RestClient.getClient().acceptFriend(userOne, userTwo).enqueue(new Callback<UserFriend>() {
            @Override
            public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

            }

            @Override
            public void onFailure(Call<UserFriend> call, Throwable t) {
                showToast("Blad podczas akceptacji zaproszenia od uzytkownika");
                Log.e("acceptUser", t.getMessage());
            }
        });
        invitationsViewPresenter.getNotAcceptedUsers(User.getUserId());
        invitationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeUser(String relationshipId) {
        try{
            RestClient.getClient().deleteFriend(relationshipId).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {

                }

                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {
                    showToast("Blad podczas usuwania zaproszenia od uzytkownika");
                    Log.e("removeUser", t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        invitationsViewPresenter.getNotAcceptedUsers(User.getUserId());
        invitationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshView() {
        invitationsViewPresenter.getNotAcceptedUsers(User.getUserId());
    }

    @Override
    public void startProfileActivity(String userId) {
        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("userid", userId));
    }

    @Override
    public void setAdapterAndGetRecyclerView() {
        invitationsAdapter = new InvitationsAdapter(friendList, getContext(), invitationsViewPresenter);
        recyclerView.setAdapter(invitationsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

    @Override
    public void onRefresh() {
        invitationsViewPresenter.getNotAcceptedUsers(User.getUserId());
        mSwipeRefreshLayout.setRefreshing(false);
        invitationsAdapter.notifyDataSetChanged();
    }
}
