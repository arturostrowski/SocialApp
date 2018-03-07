package pl.almestinio.socialapp.ui.RegisterView;

import android.app.Fragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface RegisterViewContracts {

    interface RegisterView{
        void startLoginFragment();
        void showToast(String message);
    }

    interface RegisterViewPresenter{
        void onRegisterButtonClick();
        void onLoginTextViewClick();
    }

}
