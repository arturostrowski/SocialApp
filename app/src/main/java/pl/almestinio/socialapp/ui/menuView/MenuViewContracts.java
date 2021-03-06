package pl.almestinio.socialapp.ui.menuView;

/**
 * Created by mesti193 on 3/7/2018.
 */

interface MenuViewContracts {

    interface MenuView{
        void showToast(String message);
        void changeInvitationsTabLayout(boolean isEmpty);
        void startCreatePostActivity();
        void startSearchFriendActivity(String query);
    }

    interface MenuViewPresenter{
        void getNotConfirmedFriends();
        void onAddPostMenuItemClick();
        void onSearchFriendClick(String query);
    }

}
