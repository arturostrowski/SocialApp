package pl.almestinio.socialapp.ui.fullscreenpicture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
}
