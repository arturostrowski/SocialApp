package pl.almestinio.socialapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import pl.almestinio.socialapp.database.DatabaseHelper;
import pl.almestinio.socialapp.database.DatabaseUser;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.loginView.LoginFragment;
import pl.almestinio.socialapp.ui.menuView.MenuActivity;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.getInstance(this);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();


        if(DatabaseUser.getCategories().isEmpty() || DatabaseUser.getCategories().get(0).getUserId().equals("")){
            changeFragment(new LoginFragment(), LoginFragment.class.getName());
        }else{
            User.setUserId(DatabaseUser.getCategories().get(0).getUserId());
            startActivity(new Intent(this, MenuActivity.class));
        }

    }

    public void changeFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

}
