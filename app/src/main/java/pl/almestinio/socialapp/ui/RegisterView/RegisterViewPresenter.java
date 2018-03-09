package pl.almestinio.socialapp.ui.registerView;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RegisterViewPresenter implements RegisterViewContracts.RegisterViewPresenter {

    RegisterViewContracts.RegisterView registerView;

    public RegisterViewPresenter(RegisterViewContracts.RegisterView registerView){
        this.registerView = registerView;
    }

    @Override
    public void onRegisterButtonClick(String username, String fullName, String password) {

        if(fullName.length()>0 && username.length()>0 && password.length()>0){
            RestClient.getClient().requestUsers().enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    boolean isAvailable = false;
                    for (User user : response.body().getUsers()) {
                        if (user.getUser().getEmail().equals(username)) {
                            isAvailable = false;
                            break;
                        } else {
                            isAvailable = true;
                        }
                    }

                    if (isAvailable) {
                        addUser(fullName, username, password);
                    } else {
                        registerView.showToast("Ten login jest juz zajety!");
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                }
            });
        }else if(fullName.isEmpty() || username.isEmpty() || password.isEmpty()){
            registerView.showToast("Musisz wypelnic wszystkie pola");
        }

    }

    @Override
    public void onLoginTextViewClick() {
        registerView.startLoginFragment();
    }

    private void addUser(String name, String username, String password){

        RestClient.getClient().addUser(name, username, password).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        registerView.showToast("Pomyslnie zarejestrowano!");
        registerView.startLoginFragment();
    }
}
