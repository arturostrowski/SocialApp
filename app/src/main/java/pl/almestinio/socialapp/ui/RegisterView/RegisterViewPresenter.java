package pl.almestinio.socialapp.ui.RegisterView;

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
        registerView.startLoginFragment();
    }

    @Override
    public void onLoginTextViewClick() {
        registerView.startLoginFragment();
    }
}
