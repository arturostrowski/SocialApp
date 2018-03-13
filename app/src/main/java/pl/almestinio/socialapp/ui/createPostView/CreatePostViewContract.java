package pl.almestinio.socialapp.ui.createPostView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface CreatePostViewContract {

    interface CreatePostView{
        void uploadImage();
        void showToast(String message);
        void finishActivity();
    }

    interface  CreatePostViewPresenter{
        void onCreatePostButtonClick(String text, String imageUrl);
        void onUploadImageButtonClick();
    }

}
