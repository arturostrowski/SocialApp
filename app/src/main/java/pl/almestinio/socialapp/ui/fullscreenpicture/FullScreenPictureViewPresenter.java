package pl.almestinio.socialapp.ui.fullscreenpicture;

import android.content.Context;

import pl.almestinio.socialapp.utils.DownloadImage;

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
        if(fullScreenPictureView.isPermissionGranted()){
            DownloadImage.downloadFromUrl(imageUrl, context);
        }else{
            fullScreenPictureView.showToast("Brak uprawnien...");
        }
    }

}
