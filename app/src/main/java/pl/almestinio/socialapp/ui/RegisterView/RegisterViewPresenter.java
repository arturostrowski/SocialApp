package pl.almestinio.socialapp.ui.RegisterView;

import pl.almestinio.socialapp.ui.LoginView.LoginFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RegisterViewPresenter implements RegisterViewContracts.RegisterViewPresenter {

    RegisterViewContracts.RegisterView registerView;

    public RegisterViewPresenter(RegisterViewContracts.RegisterView registerView){
        this.registerView = registerView;
    }

    @Override
    public void onRegisterButtonClick() {
        registerView.showToast("Pomyslnie zarejestrowano");
        registerView.startLoginFragment(new LoginFragment(), LoginFragment.class.getName());
    }

    @Override
    public void onLoginTextViewClick() {
        registerView.startLoginFragment(new LoginFragment(), LoginFragment.class.getName());
    }
}
