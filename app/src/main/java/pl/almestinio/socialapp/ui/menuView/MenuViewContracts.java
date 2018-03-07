package pl.almestinio.socialapp.ui.menuView;

/**
 * Created by mesti193 on 3/7/2018.
 */

interface MenuViewContracts {

    interface MenuView{
        void startCreatePostActivity();
        void startSearchFriendActivity(String query);
    }

    interface MenuViewPresenter{
        void onAddPostMenuItemClick();
        void onSearchFriendClick(String query);
    }

}
