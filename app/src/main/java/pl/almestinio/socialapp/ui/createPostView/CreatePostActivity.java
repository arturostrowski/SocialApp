package pl.almestinio.socialapp.ui.createPostView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.http.RequestsService;
import pl.almestinio.socialapp.http.UploadObject;
import pl.almestinio.socialapp.model.User;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class CreatePostActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, CreatePostViewContract.CreatePostView {

    @BindView(R.id.editTextAddPostText)
    EditText editTextAddPostText;
    @BindView(R.id.buttonUploadImage)
    Button buttonUploadImage;
    @BindView(R.id.buttonAddPost)
    Button buttonAddPost;
    @BindView(R.id.imageViewImage)
    ImageView imageViewImage;

    private static final String SERVER_PATH = "https://almestinio.pl/";
    public static final int PICK_IMAGE = 100;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;

    private String fileName="";

    private CreatePostViewContract.CreatePostViewPresenter createPostViewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        ButterKnife.bind(this);

        createPostViewPresenter = new CreatePostViewPresenter(this);

        buttonUploadImage.setOnClickListener(v -> createPostViewPresenter.onUploadImageButtonClick());
        buttonAddPost.setOnClickListener(v -> createPostViewPresenter.onCreatePostButtonClick(editTextAddPostText.getText().toString(), fileName));
    }

    @Override
    public void uploadImage() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, CreatePostActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                imageViewImage.setVisibility(View.VISIBLE);
                String filePath = getRealPathFromURIPath(uri, CreatePostActivity.this);
                buttonAddPost.setVisibility(View.GONE);
                File file = new File(filePath);
                Log.e("x", "Filename " + "user_id="+User.getUserId()+"_"+file.getName());
                fileName = "user_id="+User.getUserId()+"_"+file.getName();
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "user_id="+User.getUserId()+"_"+file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), "user_id="+User.getUserId()+"_"+file.getName());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestsService uploadImage = retrofit.create(RequestsService.class);
                Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        Toast.makeText(CreatePostActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                        Toast.makeText(CreatePostActivity.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
                        buttonAddPost.setVisibility(View.VISIBLE);
                        try{
                            Picasso.with(getApplicationContext()).load("https://almestinio.pl/phpimage/"+fileName).fit().centerCrop().into(imageViewImage);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Log.e("xd", "Error " + t.getMessage());
                    }
                });

            }else{
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, CreatePostActivity.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "user_id="+User.getUserId()+"_"+file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), "user_id="+User.getUserId()+"_"+file.getName());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestsService uploadImage = retrofit.create(RequestsService.class);
            Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                    Toast.makeText(CreatePostActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(CreatePostActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                    buttonAddPost.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    Log.e("xdddd", "Error " + t.getMessage());
                }
            });

        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("dd", "Permission has been denied");
    }
}
