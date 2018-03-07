package pl.almestinio.socialapp.ui.LoginView;

import android.app.Fragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

interface LoginViewContracts {

    interface LoginView{

        void startCreateAccountFragment(Fragment fragment, String tag);
        void startMenuActivity();
        void showToast(String message);
        void setVisibilityProgressBar(Boolean isVisible);

    }

    interface LoginViewPresenter{
        void onLoginButtonClick();
        void onCreateAccountTextViewClick();
    }

}
