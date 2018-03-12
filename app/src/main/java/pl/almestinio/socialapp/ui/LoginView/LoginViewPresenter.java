package pl.almestinio.socialapp.ui.loginView;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.almestinio.socialapp.database.DatabaseUser;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class LoginViewPresenter implements LoginViewContracts.LoginViewPresenter{

    LoginViewContracts.LoginView loginView;
    Activity context;


    public LoginViewPresenter(LoginViewContracts.LoginView loginView, Activity context){
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void onLoginButtonClick(String username, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("xD", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginView.showToast("Authentication success.");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("xD", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            loginView.showToast("Authentication failed.");
//                            updateUI(null);
                        }

                        // ...
                    }
                });

        loginView.showToast("Login Button Clicked!");
        try{
            RestClient.getClient().requestUsers().enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    for(pl.almestinio.socialapp.http.user.User user : response.body().getUsers()){
                        if(user.getUser().getEmail().equals(username) && user.getUser().getPassword().equals(password)){
                            if(DatabaseUser.getCategories().isEmpty()){
                                DatabaseUser.addOrUpdateCategories(new User(user.getUser().getUserId()));
                            }else{
                                DatabaseUser.updateCategories(user.getUser().getUserId());
                            }
                            User.setUserId(user.getUser().getUserId());
                            loginView.showToast("Poprawne dane!");
                            loginView.startMenuActivity();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        loginView.showToast("Bledne dane!");
    }

    @Override
    public void onCreateAccountTextViewClick() {
        loginView.startCreateAccountFragment();
    }

}
