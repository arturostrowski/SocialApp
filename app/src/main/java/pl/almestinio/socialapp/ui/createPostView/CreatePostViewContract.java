package pl.almestinio.socialapp.ui.createPostView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public interface CreatePostViewContract {

    interface CreatePostView{
        void createPost(String text, String imageUrl);
        void uploadImage();
        void showToast(String message);
    }

    interface  CreatePostViewPresenter{
        void onCreatePostButtonClick(String text, String imageUrl);
        void onUploadImageButtonClick();
    }

}
