package pl.almestinio.socialapp.ui.loginView;

/**
 * Created by mesti193 on 3/7/2018.
 */

interface LoginViewContracts {

    interface LoginView{
        void startCreateAccountFragment();
        void startMenuActivity();
        void showToast(String message);
    }

    interface LoginViewPresenter{
        void onLoginButtonClick();
        void onCreateAccountTextViewClick();
    }

}
