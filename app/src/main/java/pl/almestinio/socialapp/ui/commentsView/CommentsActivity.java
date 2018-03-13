package pl.almestinio.socialapp.ui.commentsView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.CommentsAdapter;
import pl.almestinio.socialapp.http.Pojodemo;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.comment.Post;
import pl.almestinio.socialapp.http.comment.Post_;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/8/2018.
 */

public class CommentsActivity extends AppCompatActivity implements CommentsViewContracts.CommentsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.buttonWriteComment)
    Button buttonWriteComment;
    @BindView(R.id.editTextAddComment)
    EditText editTextAddComment;

    private CommentsAdapter commentsAdapter;
    private List<Post_> commentsList = new ArrayList<Post_>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CommentsViewContracts.CommentsViewPresenter commentsViewPresenter;

    private Bundle bundle;
    private String bundleStringPostId;

    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        bundleStringPostId = bundle.getString("postid");

        connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_comments);
        swipeRefreshLayout.setOnRefreshListener(this);

        commentsViewPresenter = new CommentsViewPresenter(this);

        buttonWriteComment.setOnClickListener(v -> commentsViewPresenter.onWriteCommentButtonClick(bundleStringPostId, User.getUserId(), editTextAddComment.getText().toString()));

        commentsViewPresenter.getComments(isConnected, bundleStringPostId);
        setAdapterAndGetRecyclerView();
    }

    @Override
    public void startUserProfileActivity(String userId) {
        startActivity(new Intent(this, ProfileActivity.class).putExtra("userid", userId));
    }

    @Override
    public void showComments(List<Post> commentList) {
        commentsList.clear();
        try{
            for(Post comments : commentList){
                commentsList.add(new Post_(comments.getPost().getCommentId(), comments.getPost().getPostId(), comments.getPost().getUserId(), comments.getPost().getComment()));
            }
            commentsAdapter.notifyItemRangeChanged(0, commentsAdapter.getItemCount());
            commentsAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showDeletePostAlert(String commentId, String postId) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Usun komentarz")
                .setMessage("Czy chcesz usunac ten komentarz?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            RestClient.getClient().deleteComment(commentId).enqueue(new Callback<Pojodemo>() {
                                @Override
                                public void onResponse(Call<Pojodemo> call, Response<Pojodemo> response) {
                                }
                                @Override
                                public void onFailure(Call<Pojodemo> call, Throwable t) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        commentsViewPresenter.getComments(isConnected, postId);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        commentsViewPresenter.getComments(isConnected, bundleStringPostId);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshView() {
        commentsViewPresenter.getComments(isConnected, bundleStringPostId);
        editTextAddComment.setText("");
        setAdapterAndGetRecyclerView();
        commentsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setAdapterAndGetRecyclerView(){
        commentsAdapter = new CommentsAdapter(commentsList, this, commentsViewPresenter);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

}

