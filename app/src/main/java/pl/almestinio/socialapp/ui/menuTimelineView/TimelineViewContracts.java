package pl.almestinio.socialapp.ui.menuTimelineView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface TimelineViewContracts {

    interface TimelineView{
        void showToast(String message);
        void getFriendsId();
        void showPosts();
        void showPosts(int page);
        void likePost(String postId);
        void unlikePost(String postId);
        void startProfileActivity(String userId);
        void startFullScreenPicture(String imageUrl);
        void startCommentsActivity(String postId);
        void showDeletePostAlert(String postId);
        void setAdapterAndGetRecyclerView();
    }

    interface TimelineViewPresenter{
        void doToast(String message);
        void loadFriendsId();
        void loadPosts(boolean isNetworkConnection);
        void loadPosts(boolean isNetworkConnection, int page);
        void onNameTextViewClick(String userId);
        void onUserImageViewClick(String userId);
        void onImageViewClick(String imageUrl);
        void onLikeButtonClick(String postId, boolean isLiked);
        void onCommentButtonClick(String postId);
        void onDeleteImageViewClick(String postId);
    }

}
