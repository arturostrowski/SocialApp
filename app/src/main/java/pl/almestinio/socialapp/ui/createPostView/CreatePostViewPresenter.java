package pl.almestinio.socialapp.ui.createPostView;

import android.util.Log;

import java.text.SimpleDateFormat;

import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class CreatePostViewPresenter implements CreatePostViewContract.CreatePostViewPresenter {

    private CreatePostViewContract.CreatePostView createPostView;

    public CreatePostViewPresenter(CreatePostViewContract.CreatePostView createPostView){
        this.createPostView = createPostView;
    }

    @Override
    public void onCreatePostButtonClick(String text, String imageUrl) {

        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String current_time_str = time_formatter.format(System.currentTimeMillis());
        if(text.length()>0){
            if(imageUrl.equals("")){
                RestClient.getClient().createPost(User.getUserId(), text, "", current_time_str, "Public").enqueue(new Callback<Pojodemo>() {
                    @Override
                    public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                    }
                    @Override
                    public void onFailure(Call<Pojodemo> call, Throwable t) {
//                        createPostView.showToast("Wystapil blad przy dodawaniu posta");
                        Log.e("onCreatePostButtonClick", t.getMessage());
                    }
                });
            }else{
                RestClient.getClient().createPost(User.getUserId(), text, "https://almestinio.pl/phpimage/"+imageUrl, current_time_str, "Public").enqueue(new Callback<Pojodemo>() {
                    @Override
                    public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                    }
                    @Override
                    public void onFailure(Call<Pojodemo> call, Throwable t) {
//                        createPostView.showToast("Wystapil blad przy dodawaniu posta");
                        Log.e("onCreatePostButtonClick", t.getMessage());
                    }
                });
            }
            createPostView.showToast("Dodano post!");
            createPostView.finishActivity();
        }else{
            createPostView.showToast("Wiadomosc powinna zawierac minimum jeden znak");
        }
    }

    @Override
    public void onUploadImageButtonClick() {
        createPostView.uploadImage();
    }
}
