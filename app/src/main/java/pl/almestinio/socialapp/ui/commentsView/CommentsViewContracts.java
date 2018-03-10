package pl.almestinio.socialapp.ui.commentsView;

/**
 * Created by mesti193 on 3/8/2018.
 */

public interface CommentsViewContracts {

    interface CommentsView{
        void startUserProfileActivity(String userId);
        void refreshView();
        void showComments(String postId);
        void showDeletePostAlert(String commentId, String postId);
        void showToast(String message);
    }

    interface CommentsViewPresenter{
        void loadComments(boolean isNetworkConnection, String postId);
        void onNameTextViewClick(String userId);
        void onPhotoImageViewClick(String userId);
        void onDeleteImageViewClick(String commentId, String postId);
        void onWriteCommentButtonClick(String postId, String userId, String message);
    }

}
