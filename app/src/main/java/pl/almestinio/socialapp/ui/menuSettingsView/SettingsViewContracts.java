package pl.almestinio.socialapp.ui.menuSettingsView;

/**
 * Created by mesti193 on 3/10/2018.
 */

public interface SettingsViewContracts {

    interface SettingsView{
        void showToast(String message);
        void finishActivity();
        void startMainActivity();
    }

    interface SettingsViewPresenter{
        void onClickLogoutTextView();
    }

}
