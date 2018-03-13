package pl.almestinio.socialapp.ui.menuInvitationsToFriendsView;

import java.util.List;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.friend.Friend;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/11/2018.
 */

public class InvitationsViewPresenter implements InvitationsViewContracts.InvitationsViewPresenter {

    private InvitationsViewContracts.InvitationsView invitationsView;

    public InvitationsViewPresenter(InvitationsViewContracts.InvitationsView invitationsView){
        this.invitationsView = invitationsView;
    }

    @Override
    public void getNotAcceptedUsers(String userId) {
        try{
            RestClient.getClient().requestNotConfirmedFriends(User.getUserId()).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {
                    List<Friend> result = response.body().getFriends();
                    invitationsView.showNotAcceptedUsers(result);
                    invitationsView.setAdapterAndGetRecyclerView();
                }
                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUserImageViewClick(String userId) {
        invitationsView.startProfileActivity(userId);
    }

    @Override
    public void onUserTextViewClick(String userId) {
        invitationsView.startProfileActivity(userId);
    }

    @Override
    public void onConfirmButtonClick(String userId, String userTwoId) {
        invitationsView.showToast("Zaakceptowano uzytkownika");
        invitationsView.acceptUser(userId, userTwoId);
        invitationsView.refreshView();
    }

    @Override
    public void onDeleteButtonClick(String relationshipId) {
        invitationsView.showToast("Usunieto prosbe");
        invitationsView.removeUser(relationshipId);
        invitationsView.refreshView();
    }
}
