package pl.almestinio.socialapp.ui.loginView;

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


    public LoginViewPresenter(LoginViewContracts.LoginView loginView){
        this.loginView = loginView;
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
//                            DatabaseUser.deleteAllCategories();
//                            if(DatabaseUser.getCategories().isEmpty()){
//                                DatabaseUser.addOrUpdateCategories(new User(user.getUser().getUserId()));
//                            }else{
//                                DatabaseUser.updateCategories(user.getUser().getUserId());
//                            }
//                            User.setUserId(user.getUser().getUserId());
                            User.setUserId(user.getUser().getUserId());
//                            changeStatus();
//                            Intent intent = new Intent(getActivity(), MenuActivity.class);
//                            startActivity(intent);
                            loginView.showToast("Poprawne dane!");
                            loginView.startMenuActivity();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        loginView.showToast("Bledne dane!");
    }

    @Override
    public void onCreateAccountTextViewClick() {
        loginView.startCreateAccountFragment();
    }
}
