package pl.almestinio.socialapp.ui.menuSettingsView;

/**
 * Created by mesti193 on 3/10/2018.
 */

public class SettingsViewPresenter implements SettingsViewContracts.SettingsViewPresenter {

    private SettingsViewContracts.SettingsView settingsView;

    public SettingsViewPresenter(SettingsViewContracts.SettingsView settingsView){
        this.settingsView = settingsView;
    }

    @Override
    public void loadProfile(String userId) {
        settingsView.showProfileData(userId);
    }

    @Override
    public void onProfileViewClick(String userId) {
        settingsView.startProfileActivity(userId);
    }


    @Override
    public void onFriendsTextViewClick() {
        settingsView.showToast("Friends");
        settingsView.startFriendsActivity();
    }

    @Override
    public void onClickLogoutTextView() {
        settingsView.showToast("Wylogowano");
        settingsView.finishActivity();
        settingsView.startMainActivity();
    }
}
