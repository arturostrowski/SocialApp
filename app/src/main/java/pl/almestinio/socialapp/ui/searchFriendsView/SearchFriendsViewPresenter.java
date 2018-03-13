package pl.almestinio.socialapp.ui.searchFriendsView;

import java.util.List;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class SearchFriendsViewPresenter implements SearchFriendsViewContracts.FriendsViewPresenter {

    private SearchFriendsViewContracts.FriendsView friendsView;

    public SearchFriendsViewPresenter(SearchFriendsViewContracts.FriendsView friendsView){
        this.friendsView = friendsView;
    }

    @Override
    public void getUsers(String users) {
        try {
            RestClient.getClient().requestUsers2(users).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    List<User> result = response.body().getUsers();
                    friendsView.showSearchUsers(result);
                    friendsView.setAdapterAndGetRecyclerView();
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClickUser(String userId) {
        friendsView.startProfiledActivity(userId);
    }
}
