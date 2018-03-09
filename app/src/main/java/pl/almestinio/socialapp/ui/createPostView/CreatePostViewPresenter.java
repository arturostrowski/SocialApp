package pl.almestinio.socialapp.ui.createPostView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class CreatePostViewPresenter implements CreatePostViewContract.CreatePostViewPresenter {

    private CreatePostViewContract.CreatePostView createPostView;

    public CreatePostViewPresenter(CreatePostViewContract.CreatePostView createPostView){
        this.createPostView = createPostView;
    }

    @Override
    public void onCreatePostButtonClick(String text, String imageUrl) {
        createPostView.createPost(text, imageUrl);
    }

    @Override
    public void onUploadImageButtonClick() {
        createPostView.uploadImage();
    }
}
