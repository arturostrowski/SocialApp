package pl.almestinio.socialapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import pl.almestinio.socialapp.ui.loginView.LoginFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        changeFragment(new LoginFragment(), LoginFragment.class.getName());
    }

    public void changeFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

}
