package pl.almestinio.socialapp.ui.profileView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface ProfileViewContracts {

    interface ProfileView{
        void showToast(String message);
        void showUserChangePicture(String userId);
        void showUserPhoto(String userId);
        void showUserPhotoCover(String userId);
        void showUserName(String userId);
        void showUserFriendOption(String userId, String userTwoId);
        void showUserGallery(String userId);
        void showUserPosts(String userId);
        void likePost(String postId);
        void unlikePost(String postId);
        void startProfileActivity(String userId);
        void startCommentsActivity(String postId);
        void showDeletePostAlert(String postId);
        void setAdapterAndGetRecyclerView();

        void addFriend(String userId, String userTwoId, String actionUserId);
        void acceptFriend(String userId, String userTwoId);
        void removeFriend(String userId);

        void startFullScreenPicture(String imageUrl);
        void changePhoto(int id);
    }

    interface ProfileViewPresenter{
        void loadUserProfile(String userId);
        void onUserPhotoClick(String imageUrl);
        void onUserChangePhotoClick(int id);
        void onNameTextViewClick(String userId);
        void onUserImageViewClick(String userId);
        void onImageViewClick(String imageUrl);
        void onLikeButtonClick(String postId, boolean isLiked);
        void onCommentButtonClick(String postId);
        void onDeleteImageViewClick(String postId);

        void onFriendsImageOrTextViewClick(boolean isFriend, String userId, String userTwoId, String actionUserId);
        void onFriendsImageOrTextViewClick(boolean isFriend, String userId, String userTwoId);

    }

}
