package pl.almestinio.socialapp.ui.registerView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface RegisterViewContracts {

    interface RegisterView{
        void startLoginFragment();
        void showToast(String message);
    }

    interface RegisterViewPresenter{
        void onRegisterButtonClick(String username, String fullName, String password);
        void onLoginTextViewClick();
    }

}
