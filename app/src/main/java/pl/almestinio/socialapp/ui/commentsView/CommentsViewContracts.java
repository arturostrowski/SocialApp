package pl.almestinio.socialapp.ui.commentsView;

import java.util.List;

import pl.almestinio.socialapp.http.comment.Post;

/**
 * Created by mesti193 on 3/8/2018.
 */

public interface CommentsViewContracts {

    interface CommentsView{
        void startUserProfileActivity(String userId);
        void refreshView();
        void showComments(List<Post> commentList);
        void showDeletePostAlert(String commentId, String postId);
        void showToast(String message);
    }

    interface CommentsViewPresenter{
        void getComments(boolean isNetworkConnection, String postId);
        void onNameTextViewClick(String userId);
        void onPhotoImageViewClick(String userId);
        void onDeleteImageViewClick(String commentId, String postId);
        void onWriteCommentButtonClick(String postId, String userId, String message);
    }

}
