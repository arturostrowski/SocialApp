package pl.almestinio.socialapp.ui.menuInvitationsToFriendsView;

/**
 * Created by mesti193 on 3/11/2018.
 */

public interface InvitationsViewContracts {

    interface InvitationsView{

        void showToast(String message);
        void getNotAcceptedUsers(String userId);
        void startProfileActivity(String userId);
        void acceptUser(String userId, String userTwoId);
        void removeUser(String relationshipId);
        void refreshView();
        void setAdapterAndGetRecyclerView();

    }
    interface InvitationsViewPresenter{

        void loadNotAcceptedUsers(String userId);
        void onUserImageViewClick(String userId);
        void onUserTextViewClick(String userId);
        void onConfirmButtonClick(String userId, String userTwoId);
        void onDeleteButtonClick(String relationshipId);

    }

}
