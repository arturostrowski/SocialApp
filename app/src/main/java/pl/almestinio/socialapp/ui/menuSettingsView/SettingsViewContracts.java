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
        void showProfileImage(String userImage);
        void showProfileName(String userName);
        void startProfileActivity(String userId);
    }

    interface SettingsViewPresenter{
        void getProfileData();
        void onProfileViewClick(String userId);
        void onFriendsTextViewClick();
        void onClickLogoutTextView();
    }

}
