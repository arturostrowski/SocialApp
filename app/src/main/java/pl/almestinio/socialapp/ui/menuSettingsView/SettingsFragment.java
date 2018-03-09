package pl.almestinio.socialapp.ui.menuSettingsView;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.MainActivity;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.database.DatabaseUser;

/**
 * Created by mesti193 on 3/10/2018.
 */

public class SettingsFragment extends Fragment implements SettingsViewContracts.SettingsView {

    @BindView(R.id.textViewLogout)
    TextView textViewLogout;
    @BindView(R.id.imageViewLogout)
    ImageView imageViewLogout;

    private SettingsViewContracts.SettingsViewPresenter settingsViewPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_menu, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        settingsViewPresenter = new SettingsViewPresenter(this);

        textViewLogout.setOnClickListener(v -> settingsViewPresenter.onClickLogoutTextView());
        imageViewLogout.setOnClickListener(v -> settingsViewPresenter.onClickLogoutTextView());

        return view;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        DatabaseUser.updateCategories("");
        getActivity().finish();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
