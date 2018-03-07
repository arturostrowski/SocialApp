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
    public void onNameTextViewClick(String fullName) {
        timelineView.showToast(fullName);
    }

    @Override
    public void onUserImageViewClick() {

    }

    @Override
    public void onDeleteButtonClick() {

    }

    @Override
    public void onImageViewClick() {

    }

    @Override
    public void onLikeButtonClick() {

    }

    @Override
    public void onCommentButtonClick() {

    }
}
