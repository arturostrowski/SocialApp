package pl.almestinio.socialapp.ui.loginView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.ui.menuView.MenuActivity;
import pl.almestinio.socialapp.ui.registerView.RegisterFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class LoginFragment extends Fragment implements LoginViewContracts.LoginView{

    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textViewRegister)
    TextView textViewRegister;
    @BindView(R.id.editTextUsername)
    EditText editTextUsername;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    private LoginViewContracts.LoginViewPresenter loginViewPresenter;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        fragmentManager = getFragmentManager();

        loginViewPresenter = new LoginViewPresenter(this, getActivity());

        buttonLogin.setOnClickListener(v -> loginViewPresenter.onLoginButtonClick(editTextUsername.getText().toString(), editTextPassword.getText().toString()));
        textViewRegister.setOnClickListener(v -> loginViewPresenter.onCreateAccountTextViewClick());

        return view;
    }


    @Override
    public void startCreateAccountFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new RegisterFragment());
        fragmentTransaction.addToBackStack(RegisterFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void startMenuActivity() {
        startActivity(new Intent(getActivity(), MenuActivity.class));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
