package pl.almestinio.socialapp.ui.menuTimelineView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface TimelineViewContracts {

    interface TimelineView{
        void showToast(String message);
        void startProfileActivity(String userId);
        void startFullScreenPicture(String imageUrl);
        void startCommentsActivity(String postId);
        void showDeletePostAlert(String postId);
    }

    interface TimelineViewPresenter{
        void loadPosts();
        void onNameTextViewClick(String userId);
        void onUserImageViewClick(String userId);
        void onImageViewClick(String imageUrl);
        void onLikeButtonClick(String postId);
        void onCommentButtonClick(String postId);
        void onDeleteImageViewClick(String postId);
    }

}
