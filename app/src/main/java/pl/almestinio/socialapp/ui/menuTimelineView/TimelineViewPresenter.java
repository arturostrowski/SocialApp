package pl.almestinio.socialapp.ui.menuTimelineView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineViewPresenter implements TimelineViewContracts.TimelineViewPresenter {

    TimelineViewContracts.TimelineView timelineView;

    public TimelineViewPresenter(TimelineViewContracts.TimelineView timelineView){
        this.timelineView = timelineView;
    }

    @Override
    public void loadPosts(boolean isNetworkConnection) {
        if(isNetworkConnection){
            timelineView.showToast("Load post");
            timelineView.showPosts();
            timelineView.setAdapterAndGetRecyclerView();
        }else{
            timelineView.showToast("Brak polaczenia z internetem");
        }
    }

    @Override
    public void onNameTextViewClick(String userId) {
        timelineView.showToast(userId);
        timelineView.startProfileActivity(userId);
    }

    @Override
    public void onUserImageViewClick(String userId) {
        timelineView.showToast(userId);
        timelineView.startProfileActivity(userId);
    }

    @Override
    public void onImageViewClick(String imageUrl) {
        timelineView.showToast(imageUrl);
        timelineView.startFullScreenPicture(imageUrl);
    }

    @Override
    public void onLikeButtonClick(String postId, boolean isLiked) {
        timelineView.showToast("LikeButtonClicked");
        if(isLiked){
            timelineView.unlikePost(postId);
        }else{
            timelineView.likePost(postId);
        }
    }

    @Override
    public void onCommentButtonClick(String postId) {
        timelineView.showToast("CommentButtonClicked");
        timelineView.startCommentsActivity(postId);
    }

    @Override
    public void onDeleteImageViewClick(String postId) {
        timelineView.showToast("DeletePostClicked");
        timelineView.showDeletePostAlert(postId);
    }
}
