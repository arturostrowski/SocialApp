package pl.almestinio.socialapp.ui.registerView;

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
import pl.almestinio.socialapp.ui.loginView.LoginFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RegisterFragment extends Fragment implements RegisterViewContracts.RegisterView{

    @BindView(R.id.buttonRegister)
    Button buttonRegister;
    @BindView(R.id.textViewLogin)
    TextView textViewLogin;
    @BindView(R.id.editTextRegisterFullName)
    EditText editTextRegisterFullName;
    @BindView(R.id.editTextRegisterUsername)
    EditText editTextRegisterUsername;
    @BindView(R.id.editTextRegisterPassword)
    EditText editTextRegisterPassword;

    RegisterViewContracts.RegisterViewPresenter registerView;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ButterKnife.bind(this, view);
        fragmentManager = getFragmentManager();

        registerView = new RegisterViewPresenter(this, getActivity());

        buttonRegister.setOnClickListener(v -> registerView.onRegisterButtonClick(editTextRegisterUsername.getText().toString(), editTextRegisterFullName.getText().toString(), editTextRegisterPassword.getText().toString()));
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
