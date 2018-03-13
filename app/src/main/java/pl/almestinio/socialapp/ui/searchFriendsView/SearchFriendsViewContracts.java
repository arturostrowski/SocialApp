package pl.almestinio.socialapp.ui.searchFriendsView;

import java.util.List;

import pl.almestinio.socialapp.http.user.User;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface SearchFriendsViewContracts {

    interface FriendsView{
        void showToast(String message);
        void showSearchUsers(List<User> usersList);
        void setAdapterAndGetRecyclerView();
        void startProfiledActivity(String userId);
    }

    interface FriendsViewPresenter{
        void getUsers(String users);
        void onClickUser(String userId);
    }

}
