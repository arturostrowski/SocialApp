package pl.almestinio.socialapp.ui.commentsView;

import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/8/2018.
 */

public class CommentsViewPresenter implements CommentsViewContracts.CommentsViewPresenter {

    private CommentsViewContracts.CommentsView commentsView;

    public CommentsViewPresenter(CommentsViewContracts.CommentsView commentsView){
        this.commentsView = commentsView;
    }

    @Override
    public void loadComments(boolean isNetworkConnection, String postId) {
        if(isNetworkConnection){
            commentsView.showToast("Load comments");
            commentsView.showComments(postId);
        }else{
            commentsView.showToast("Brak polaczenia z internetem");
        }
    }

    @Override
    public void onNameTextViewClick(String userId) {
        commentsView.showToast("onNameTextViewClick");
        commentsView.startUserProfileActivity(userId);
    }

    @Override
    public void onPhotoImageViewClick(String userId) {
        commentsView.showToast("onPhotoImageViewClick");
        commentsView.startUserProfileActivity(userId);
    }

    @Override
    public void onDeleteImageViewClick(String commentId) {
        commentsView.showToast("onDeleteImageViewClick");
    }

    @Override
    public void onWriteCommentButtonClick(String postId, String userId, String message) {
        try {
            RestClient.getClient().writeComment(postId, userId, message).enqueue(new Callback<Pojodemo>() {
                @Override
                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {}
                @Override
                public void onFailure(Call<Pojodemo> call, Throwable t) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        commentsView.refreshView();
        commentsView.showToast("Dodano komentarz!");
    }

}
