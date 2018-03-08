package pl.almestinio.socialapp.ui.fullscreenpictureView;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class FullScreenPictureActivity extends AppCompatActivity implements FullScreenPictureViewContracts.FullScreenPictureView {

    @BindView(R.id.imageViewSavePhoto)
    ImageView imageViewSavePhoto;
    @BindView(R.id.imageViewFullScreenPicture)
    PhotoView imageViewFullScreenPicture;

    private PhotoViewAttacher photoViewAttacher;
    private Bundle bundle;

    private FullScreenPictureViewContracts.FullScreenPictureViewPresenter fullScreenPictureViewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fullscreenpicture);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        bundle = getIntent().getExtras();
        fullScreenPictureViewPresenter = new FullScreenPictureViewPresenter(this, getApplicationContext());

        Picasso.with(getApplicationContext()).load(bundle.getString("imageurl")).fit().centerInside().into(imageViewFullScreenPicture);

        photoViewAttacher = new PhotoViewAttacher(imageViewFullScreenPicture);
        photoViewAttacher.update();


        imageViewSavePhoto.setOnClickListener(v -> fullScreenPictureViewPresenter.downloadPicture(bundle.getString("imageurl")));

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isPermissionGranted() {

        int permission = ContextCompat.checkSelfPermission(FullScreenPictureActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("grant", "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Aplikacja nie posiada uprawnien do zapisu plikow na telefonie.")
                        .setTitle("Brak uprawnien");

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("grant", "Clicked");
                        makeRequest();

                    }

                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {

                //makeRequest1();
                makeRequest();
                return true;
            }
            return false;
        }

        return true;
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                500);
    }
}
