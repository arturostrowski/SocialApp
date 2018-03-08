package pl.almestinio.socialapp.ui.menuView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class MenuViewPresenter implements MenuViewContracts.MenuViewPresenter {

    MenuViewContracts.MenuView menuView;

    public MenuViewPresenter(MenuViewContracts.MenuView menuView){
        this.menuView = menuView;
    }


    @Override
    public void onAddPostMenuItemClick() {
        menuView.showToast("AddPostClicked");
        menuView.startCreatePostActivity();
    }

    @Override
    public void onSearchFriendClick(String query) {
        menuView.showToast("SearchFriendsClicked");
        menuView.startSearchFriendActivity(query);
    }
}
