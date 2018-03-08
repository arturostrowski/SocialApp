package pl.almestinio.socialapp.ui.fullscreenpicture;

import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mesti193 on 3/8/2018.
 */

public class FullScreenPictureViewPresenter implements FullScreenPictureViewContracts.FullScreenPictureViewPresenter {

    FullScreenPictureViewContracts.FullScreenPictureView fullScreenPictureView;
    Context context;

    public FullScreenPictureViewPresenter(FullScreenPictureViewContracts.FullScreenPictureView fullScreenPictureView, Context context){
        this.fullScreenPictureView = fullScreenPictureView;
        this.context = context;
    }

    @Override
    public void downloadPicture(String imageUrl) {
        fullScreenPictureView.showToast("Pobieranie zdjecia..."+" "+imageUrl);
        file_download(imageUrl);

    }

    public void file_download(String uRl) {
        try{
            File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/SocialApp/");

            if (!direct.exists()) {
                direct.mkdirs();
            }

            DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(uRl);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri);

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Demo")
                    .setDescription("Something useful. No, really.")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SocialApp/postphoto.jpg");

            mgr.enqueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
