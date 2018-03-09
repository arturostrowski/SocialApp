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
        profileView.showUserChangePicture(userId);
        profileView.showUserGallery(userId);
        profileView.showUserPosts(userId);
        profileView.setAdapterAndGetRecyclerView();
    }

    @Override
    public void onUserPhotoClick(String imageUrl) {
        profileView.startFullScreenPicture(imageUrl);
    }

    @Override
    public void onUserChangePhotoClick(int id) {
        profileView.changePhoto(id);
    }

    @Override
    public void onNameTextViewClick(String userId) {
        profileView.showToast(userId);
        profileView.startProfileActivity(userId);
    }

    @Override
    public void onUserImageViewClick(String userId) {
        profileView.showToast(userId);
        profileView.startProfileActivity(userId);
    }

    @Override
    public void onImageViewClick(String imageUrl) {
        profileView.showToast(imageUrl);
        profileView.startFullScreenPicture(imageUrl);
    }

    @Override
    public void onLikeButtonClick(String postId, boolean isLiked) {
        profileView.showToast("LikeButtonClicked");
        if(isLiked){
            profileView.unlikePost(postId);
        }else{
            profileView.likePost(postId);
        }
    }

    @Override
    public void onCommentButtonClick(String postId) {
        profileView.showToast("CommentButtonClicked");
        profileView.startCommentsActivity(postId);
    }

    @Override
    public void onDeleteImageViewClick(String postId) {
        profileView.showToast("DeletePostClicked");
        profileView.showDeletePostAlert(postId);
    }
}
