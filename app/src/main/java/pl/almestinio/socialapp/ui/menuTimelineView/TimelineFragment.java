package pl.almestinio.socialapp.ui.menuTimelineView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.TimelineAdapter;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.post.Post;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.ui.menuView.MenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineFragment extends Fragment implements TimelineViewContracts.TimelineView{

    private TimelineAdapter timelineAdapter;
    private List<Post_> postsList = new ArrayList<Post_>();
    private RecyclerView recyclerView;

    private TimelineViewContracts.TimelineViewPresenter timelineViewPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        setHasOptionsMenu(true);

        timelineViewPresenter = new TimelineViewPresenter(this);

        if(!postsList.isEmpty()){
            postsList.clear();
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTimelinePosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getPosts();
        setAdapterAndGetRecyclerView();

        return view;
    }

    private void getPosts(){
        postsList.clear();
        try{
            RestClient.getClient().requestPosts("").enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    for(Post posts : response.body().getPosts()){
                        postsList.add(new Post_(posts.getPost().getPostId().toString(), posts.getPost().getUserId().toString(), posts.getPost().getPostTxt().toString(), posts.getPost().getPostPic().toString(), posts.getPost().getPostTime().toString(), posts.getPost().getPriority().toString()));
                        try{
                            timelineAdapter.notifyItemRangeChanged(0,timelineAdapter.getItemCount());
                            timelineAdapter.notifyDataSetChanged();
                        }catch (Exception e){}
                    }
                }
                @Override
                public void onFailure(Call<Posts> call, Throwable t) {}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapterAndGetRecyclerView(){
        timelineAdapter = new TimelineAdapter(postsList, getContext(), timelineViewPresenter);
        recyclerView.setAdapter(timelineAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startProfileActivity(String userId) {
//        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("userid", userId));
    }

    @Override
    public void startFullScreenPicture(String imageUrl) {
//        startActivity(new Intent(getActivity(), FullScreenPictureActivity.class).putExtra("imageurl", imageUrl));
    }

    @Override
    public void startCommentsActivity(String postId) {
//        startActivity(new Intent(getActivity(), CommentsActivity.class).putExtra("postid", postId));
    }
}
