package pl.almestinio.socialapp.ui.menuSettingsView;

/**
 * Created by mesti193 on 3/10/2018.
 */

public interface SettingsViewContracts {

    interface SettingsView{
        void showToast(String message);
        void finishActivity();
        void startMainActivity();
        void startFriendsActivity();
        void showProfileData(String userId);
        void startProfileActivity(String userId);
    }

    interface SettingsViewPresenter{
        void loadProfile(String userId);
        void onProfileViewClick(String userId);
        void onFriendsTextViewClick();
        void onClickLogoutTextView();
    }

}
