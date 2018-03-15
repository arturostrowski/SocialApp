package pl.almestinio.socialapp.ui.menuTimelineView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.TimelineAdapter;
import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.post.Post;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.ui.commentsView.CommentsActivity;
import pl.almestinio.socialapp.ui.fullScreenPictureView.FullScreenPictureActivity;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;
import pl.almestinio.socialapp.utils.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TimelineViewContracts.TimelineView{

    private TimelineAdapter timelineAdapter;
    private List<Post_> postsList = new ArrayList<Post_>();
    private List<String> relationshipList = new ArrayList<String>();
    private String relationshipIds;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    private TimelineViewContracts.TimelineViewPresenter timelineViewPresenter;

    private boolean isConnected;

    private boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

    int actuallyPage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        setHasOptionsMenu(true);

        timelineViewPresenter = new TimelineViewPresenter(this);

        isConnected = NetworkConnection.isNetworkConnection(getContext());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTimelinePosts);
        layoutManager = new LinearLayoutManager(getContext());

        timelineViewPresenter.getFriendsId(isConnected, actuallyPage);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        onRefresh();
    }

    @Override
    public void setAdapterAndGetRecyclerView(){
        timelineAdapter = new TimelineAdapter(postsList, getContext(), timelineViewPresenter);
        recyclerView.setAdapter(timelineAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("SCROLL", "onScrollStateChanged");
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
                totalItems = layoutManager.getItemCount();

                if(isScrolling && (currentItems+scrollOutItems == totalItems)){
                    isScrolling = false;
                    fetchData();
                    Log.i("SCROLL", "onScrolled");
                }
            }
        });
    }

    private void fetchData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    timelineViewPresenter.getPosts(isConnected, actuallyPage, relationshipIds);
                }catch (Exception e){

                }
            }
        }, 10);
    }

    @Override
    public void onRefresh() {
        relationshipIds="";
        relationshipList.clear();
        isConnected = NetworkConnection.isNetworkConnection(getContext());
        actuallyPage = 0;
        postsList.clear();
        timelineViewPresenter.getFriendsId(isConnected, actuallyPage);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getFriends(String relationshipIds) {
        this.relationshipIds = relationshipIds;

        timelineViewPresenter.getPosts(isConnected, actuallyPage, relationshipIds);
    }

    @Override
    public void showPosts(List<Post> postList) {
        actuallyPage++;
        relationshipList.clear();
        try{
            for(Post posts : postList){
                postsList.add(new Post_(posts.getPost().getPostId().toString(), posts.getPost().getUserId().toString(), posts.getPost().getPostTxt().toString(), posts.getPost().getPostPic().toString(), posts.getPost().getPostTime().toString(), posts.getPost().getPriority().toString()));
            }
            timelineAdapter.notifyItemRangeChanged(0,timelineAdapter.getItemCount());
            timelineAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void startProfileActivity(String userId) {
        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("userid", userId));
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
                            RestClient.getClient().deletePost(postId).enqueue(new Callback<Pojodemo>() {
                                @Override
                                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {

                                }

                                @Override
                                public void onFailure(Call<Pojodemo> call, Throwable t) {
                                    showToast("Blad podczas usuwania posta");
                                    Log.e("showDeletePostAlert", t.getMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isConnected = NetworkConnection.isNetworkConnection(getContext());
                        timelineViewPresenter.getFriendsId(isConnected, actuallyPage);
                        setAdapterAndGetRecyclerView();
                        timelineAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }

    @Override
    public void refresh() {
        timelineAdapter.notifyDataSetChanged();
    }
}
