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
    public void onClickLogoutTextView() {
        settingsView.showToast("Wylogowano");
        settingsView.finishActivity();
        settingsView.startMainActivity();
    }
}
