package pl.almestinio.socialapp.ui.registerView;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RegisterViewPresenter implements RegisterViewContracts.RegisterViewPresenter {

    RegisterViewContracts.RegisterView registerView;
    Activity context;

    public RegisterViewPresenter(RegisterViewContracts.RegisterView registerView, Activity context){
        this.registerView = registerView;
        this.context = context;
    }

    @Override
    public void onRegisterButtonClick(String username, String fullName, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("x", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("x", "createUserWithEmail:failure", task.getException());

                }
            }
        });



        if(fullName.length()>0 && username.length()>0 && password.length()>0){
            RestClient.getClient().requestUsers().enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    boolean isAvailable = false;
                    for (User user : response.body().getUsers()) {
                        if (user.getUser().getEmail().equals(username)) {
                            isAvailable = false;
                            break;
                        } else {
                            isAvailable = true;
                        }
                    }

                    if (isAvailable) {
                        addUser(fullName, username, password);
                    } else {
                        registerView.showToast("Ten login jest juz zajety!");
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                }
            });
        }else if(fullName.isEmpty() || username.isEmpty() || password.isEmpty()){
            registerView.showToast("Musisz wypelnic wszystkie pola");
        }

    }

    @Override
    public void onLoginTextViewClick() {
        registerView.startLoginFragment();
    }

    private void addUser(String name, String username, String password){

        RestClient.getClient().addUser(name, username, password).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        registerView.showToast("Pomyslnie zarejestrowano!");
        registerView.startLoginFragment();
    }
}
