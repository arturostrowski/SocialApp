package pl.almestinio.socialapp.ui.menuTimelineView;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface TimelineViewContracts {

    interface TimelineView{
        void showToast(String message);
    }

    interface TimelineViewPresenter{
        void onNameTextViewClick(String fullName);
        void onUserImageViewClick();
        void onDeleteButtonClick();
        void onImageViewClick();
        void onLikeButtonClick();
        void onCommentButtonClick();
    }

}
