package pl.almestinio.socialapp.ui.profileView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface ProfileViewContracts {

    interface ProfileView{
        void showToast(String message);
        void showUserPhoto(String userId);
        void showUserPhotoCover(String userId);
        void showUserName(String userId);
        void showUserGallery(String userId);
        void setAdapterAndGetRecyclerView();

        void startFullScreenPicture(String imageUrl);
    }

    interface ProfileViewPresenter{
        void loadUserProfile(String userId);
        void onUserPhotoClick(String imageUrl);
    }

}
