package pl.almestinio.socialapp.ui.menuTimelineView;

import java.util.List;

import pl.almestinio.socialapp.http.post.Post;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface TimelineViewContracts {

    interface TimelineView{
        void showToast(String message);
        void getFriends(String relationshipIds);
        void showPosts(List<Post> postList);
        void startProfileActivity(String userId);
        void startFullScreenPicture(String imageUrl);
        void startCommentsActivity(String postId);
        void showDeletePostAlert(String postId);
        void refresh();
        void setAdapterAndGetRecyclerView();
    }

    interface TimelineViewPresenter{
        void getFriendsId(boolean isConnected, int actuallyPage);
        void getPosts(boolean isNetworkConnection, int page, String relationshipIds);
        void onNameTextViewClick(String userId);
        void onUserImageViewClick(String userId);
        void onImageViewClick(String imageUrl);
        void onLikeButtonClick(String postId, boolean isLiked);
        void onCommentButtonClick(String postId);
        void onDeleteImageViewClick(String postId);
    }

}
