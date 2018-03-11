package pl.almestinio.socialapp.ui.menuInvitationsToFriendsView;

/**
 * Created by mesti193 on 3/11/2018.
 */

public class InvitationsViewPresenter implements InvitationsViewContracts.InvitationsViewPresenter {

    private InvitationsViewContracts.InvitationsView invitationsView;

    public InvitationsViewPresenter(InvitationsViewContracts.InvitationsView invitationsView){
        this.invitationsView = invitationsView;
    }

    @Override
    public void loadNotAcceptedUsers(String userId) {
        invitationsView.getNotAcceptedUsers(userId);
        invitationsView.setAdapterAndGetRecyclerView();
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
