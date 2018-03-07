package pl.almestinio.socialapp.ui.RegisterView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.ui.LoginView.LoginFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RegisterFragment extends Fragment implements RegisterViewContracts.RegisterView{

    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.textViewLogin)
    TextView textViewLogin;

    RegisterViewContracts.RegisterViewPresenter registerView;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ButterKnife.bind(this, view);
        fragmentManager = getFragmentManager();

        registerView = new RegisterViewPresenter(this);

        buttonRegister.setOnClickListener(v -> registerView.onRegisterButtonClick());
        textViewLogin.setOnClickListener(v -> registerView.onLoginTextViewClick());

        return view;
    }

    @Override
    public void startLoginFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
        fragmentTransaction.addToBackStack(LoginFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
