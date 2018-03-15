package pl.almestinio.socialapp.ui.menuView;

import android.util.Log;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class MenuViewPresenter implements MenuViewContracts.MenuViewPresenter {

    MenuViewContracts.MenuView menuView;

    public MenuViewPresenter(MenuViewContracts.MenuView menuView){
        this.menuView = menuView;
    }


    @Override
    public void getNotConfirmedFriends() {
        try{
            RestClient.getClient().requestNotConfirmedFriends(User.getUserId()).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {
                    if(response.body().getFriends().isEmpty()){
                        menuView.changeInvitationsTabLayout(true);
                    }else{
                        menuView.changeInvitationsTabLayout(false);
                    }
                }
                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {
                    menuView.showToast("Blad podczas pobierania i zmiany layoutu nie potwierdzonych uzytkownikow");
                    Log.e("getNotConfirmedFriends", t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
