package pl.almestinio.socialapp.ui.loginView;

import android.app.Activity;
import android.util.Log;

import pl.almestinio.socialapp.database.DatabaseUser;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class LoginViewPresenter implements LoginViewContracts.LoginViewPresenter{

    LoginViewContracts.LoginView loginView;
    Activity context;


    public LoginViewPresenter(LoginViewContracts.LoginView loginView, Activity context){
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void onLoginButtonClick(String username, String password) {

        loginView.showToast("Login Button Clicked!");
        try{
            RestClient.getClient().requestUsers().enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    for(pl.almestinio.socialapp.http.user.User user : response.body().getUsers()){
                        if(user.getUser().getEmail().equals(username) && user.getUser().getPassword().equals(password)){
                            if(DatabaseUser.getUser().isEmpty()){
                                DatabaseUser.addOrUpdateUser(new User(user.getUser().getUserId()));
                            }else{
                                DatabaseUser.updateUser(user.getUser().getUserId());
                            }
                            User.setUserId(user.getUser().getUserId());
                            loginView.showToast("Poprawne dane!");
                            loginView.startMenuActivity();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    loginView.showToast("Blad podczas logowania ( nie poprawne dane/blad serwera )");
                    Log.e("onLoginButtonClick", t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateAccountTextViewClick() {
        loginView.startCreateAccountFragment();
    }

}
