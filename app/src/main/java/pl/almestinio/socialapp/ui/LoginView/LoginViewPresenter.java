package pl.almestinio.socialapp.ui.LoginView;

import pl.almestinio.socialapp.ui.RegisterView.RegisterFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class LoginViewPresenter implements LoginViewContracts.LoginViewPresenter{

    LoginViewContracts.LoginView loginView;

    public LoginViewPresenter(LoginViewContracts.LoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void onLoginButtonClick() {
        loginView.showToast("Login Button Clicked!");
        loginView.startMenuActivity();
    }

    @Override
    public void onCreateAccountTextViewClick() {
        loginView.startCreateAccountFragment(new RegisterFragment(), RegisterFragment.class.getName());
    }
}
