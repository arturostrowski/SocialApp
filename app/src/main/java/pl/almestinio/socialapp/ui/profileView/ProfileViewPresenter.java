package pl.almestinio.socialapp.ui.profileView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class ProfileViewPresenter implements ProfileViewContracts.ProfileViewPresenter {

    private ProfileViewContracts.ProfileView profileView;

    public ProfileViewPresenter(ProfileViewContracts.ProfileView profileView){
        this.profileView = profileView;
    }


    @Override
    public void loadUserProfile(String userId) {
        profileView.showToast("Ladowanie profilu uzytkownika");
        profileView.showUserName(userId);
        profileView.showUserPhoto(userId);
        profileView.showUserPhotoCover(userId);
        profileView.showUserGallery(userId);
        profileView.setAdapterAndGetRecyclerView();
    }

    @Override
    public void onUserPhotoClick(String imageUrl) {
        profileView.startFullScreenPicture(imageUrl);
    }
}
