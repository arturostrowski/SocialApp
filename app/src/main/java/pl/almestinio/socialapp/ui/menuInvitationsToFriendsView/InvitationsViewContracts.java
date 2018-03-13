package pl.almestinio.socialapp.ui.menuInvitationsToFriendsView;

import java.util.List;

import pl.almestinio.socialapp.http.friend.Friend;

/**
 * Created by mesti193 on 3/11/2018.
 */

public interface InvitationsViewContracts {

    interface InvitationsView{

        void showToast(String message);
        void showNotAcceptedUsers(List<Friend> userFriendList);
        void acceptUser(String userId, String userTwoId);
        void removeUser(String relationshipId);
        void refreshView();
        void startProfileActivity(String userId);
        void setAdapterAndGetRecyclerView();

    }
    interface InvitationsViewPresenter{
        void getNotAcceptedUsers(String userId);
        void onUserImageViewClick(String userId);
        void onUserTextViewClick(String userId);
        void onConfirmButtonClick(String userId, String userTwoId);
        void onDeleteButtonClick(String relationshipId);

    }

}
