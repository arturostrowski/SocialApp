package pl.almestinio.socialapp.ui.fullscreenpictureView;

/**
 * Created by mesti193 on 3/8/2018.
 */

public class FullScreenPictureViewContracts {

    interface FullScreenPictureView{
        void showToast(String message);
        boolean isPermissionGranted();
    }

    interface FullScreenPictureViewPresenter{
        void downloadPicture(String imageUrl);
    }

}
