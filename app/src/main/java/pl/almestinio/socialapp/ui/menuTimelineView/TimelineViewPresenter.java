package pl.almestinio.socialapp.ui.menuTimelineView;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.friend.Friend;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.http.post.Post;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineViewPresenter implements TimelineViewContracts.TimelineViewPresenter {

    private List<String> result = new ArrayList<String>();
    private List<Post> postResult = new ArrayList<Post>();
    private String relationshipIds;

    private TimelineViewContracts.TimelineView timelineView;

    public TimelineViewPresenter(TimelineViewContracts.TimelineView timelineView){
        this.timelineView = timelineView;
    }

    @Override
    public void getFriendsId(boolean isConnected, int actuallyPage) {
        timelineView.setAdapterAndGetRecyclerView();
        try {
            RestClient.getClient().requestFriends(User.getUserId()).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {
                    int j = 0;
                    for(Friend userFriend : response.body().getFriends()){
                        if(userFriend.getFriend().getUserOneId().equals(User.getUserId())){
                            result.add(j, userFriend.getFriend().getUserTwoId());
                            j++;
                        }else{
                            result.add(j, userFriend.getFriend().getUserOneId());
                            j++;
                        }
                    }
                    relationshipIds = User.getUserId();
                    for(String id : result){
                        relationshipIds = relationshipIds + "," + id;
                    }
                    timelineView.getFriends(relationshipIds);

                }
                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {
                    timelineView.showToast("Blad podczas pobierania id uzytkownikow z serwera");
                    Log.e("getFriendsId", t.getMessage());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getPosts(boolean isNetworkConnection, int page, String relationshipIds) {
        if(isNetworkConnection){
            try{
                RestClient.getClient().requestPosts(relationshipIds, page).enqueue(new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        if(response.body().getPosts().isEmpty()){
                            timelineView.showToast("Koniec aktywnosci");
                        }else{
                            postResult = response.body().getPosts();
                            timelineView.showToast("Load post");
                            timelineView.showPosts(postResult);
                        }
                    }
                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {
                        timelineView.showToast("Blad podczas pobierania postow z serwera");
                        Log.e("getPosts", t.getMessage());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
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
            try{
                RestClient.getClient().deleteLike(postId, User.getUserId()).enqueue(new Callback<Pojodemo>() {
                    @Override
                    public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                    }
                    @Override
                    public void onFailure(Call<Pojodemo> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                timelineView.refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                RestClient.getClient().likePost(postId, User.getUserId()).enqueue(new Callback<Pojodemo>() {
                    @Override
                    public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                    }
                    @Override
                    public void onFailure(Call<Pojodemo> call, Throwable t) {
                    }
                });
                timelineView.refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
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
