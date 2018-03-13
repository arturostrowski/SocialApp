package pl.almestinio.socialapp.ui.menuSettingsView;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/10/2018.
 */

public class SettingsViewPresenter implements SettingsViewContracts.SettingsViewPresenter {

    private SettingsViewContracts.SettingsView settingsView;

    public SettingsViewPresenter(SettingsViewContracts.SettingsView settingsView){
        this.settingsView = settingsView;
    }

    @Override
    public void getProfileData() {
        try{
            RestClient.getClient().requestUserPhoto(User.getUserId()).enqueue(new Callback<UserPhoto>() {
                @Override
                public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                    for(UsersPic userPic : response.body().getUsersPic()){
                        if(!userPic.getUserPic().getImage().isEmpty()){
                            settingsView.showProfileImage(userPic.getUserPic().getImage().toString());
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserPhoto> call, Throwable t) {

                }
            });
            RestClient.getClient().requestUser(User.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String userName = response.body().getUsers().get(0).getUser().getName();
                    settingsView.showProfileName(userName);
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void onProfileViewClick(String userId) {
        settingsView.startProfileActivity(userId);
    }


    @Override
    public void onFriendsTextViewClick() {
        settingsView.showToast("Friends");
        settingsView.startFriendsActivity();
    }

    @Override
    public void onClickLogoutTextView() {
        settingsView.showToast("Wylogowano");
        settingsView.finishActivity();
        settingsView.startMainActivity();
    }
}
