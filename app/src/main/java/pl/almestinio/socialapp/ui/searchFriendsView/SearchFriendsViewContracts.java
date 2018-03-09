package pl.almestinio.socialapp.ui.searchFriendsView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface SearchFriendsViewContracts {

    interface FriendsView{
        void showToast(String message);
        void showSearchUsers(String users);
        void setAdapterAndGetRecyclerView();
        void startProfiledActivity(String userId);
    }

    interface FriendsViewPresenter{
        void loadSearchFriendsView(String users);
        void onClickUser(String userId);
    }

}
