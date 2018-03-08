package pl.almestinio.socialapp.ui.menuTimelineView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import pl.almestinio.socialapp.ui.commentsView.CommentsActivity;
import pl.almestinio.socialapp.ui.fullscreenpictureView.FullScreenPictureActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TimelineViewContracts.TimelineView{

    private TimelineAdapter timelineAdapter;
    private List<Post_> postsList = new ArrayList<Post_>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TimelineViewContracts.TimelineViewPresenter timelineViewPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        setHasOptionsMenu(true);

        timelineViewPresenter = new TimelineViewPresenter(this);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTimelinePosts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        getPosts();
        setAdapterAndGetRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        timelineViewPresenter.loadPosts();
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
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getPosts();
        swipeRefreshLayout.setRefreshing(false);
        timelineAdapter.notifyDataSetChanged();
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
        startActivity(new Intent(getActivity(), FullScreenPictureActivity.class).putExtra("imageurl", imageUrl));
    }

    @Override
    public void startCommentsActivity(String postId) {
        startActivity(new Intent(getActivity(), CommentsActivity.class).putExtra("postid", postId));
    }

    @Override
    public void showDeletePostAlert(String postId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Usun post")
                .setMessage("Czy chcesz usunac ten post?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
//                            RestClient.getClient().deletePost(postId).enqueue(new Callback<Pojodemo>() {
//                                @Override
//                                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<Pojodemo> call, Throwable t) {
//
//                                }
//                            });
                            Log.i("USUN", "XDDD");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getPosts();
                        setAdapterAndGetRecyclerView();
                        timelineAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }
}
