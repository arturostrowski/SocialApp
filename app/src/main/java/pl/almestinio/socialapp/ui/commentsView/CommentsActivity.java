package pl.almestinio.socialapp.ui.commentsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.comment.Comments;
import pl.almestinio.socialapp.http.comment.Post;
import pl.almestinio.socialapp.http.comment.Post_;
import pl.almestinio.socialapp.model.User;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewComments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_comments);
        swipeRefreshLayout.setOnRefreshListener(this);

        commentsViewPresenter = new CommentsViewPresenter(this);

        buttonWriteComment.setOnClickListener(v -> commentsViewPresenter.onWriteCommentButtonClick(bundle.getString("postid"), User.getUserId(), editTextAddComment.getText().toString()));

        getComments(bundle.getString("postid"));
        setAdapterAndGetRecyclerView();
    }

    private void getComments(String postId){
        commentsList.clear();
        try {
            RestClient.getClient().requestComments(postId).enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    for(Post post : response.body().getPosts()) {
                        commentsList.add(new Post_(post.getPost().getCommentId(), post.getPost().getPostId(), post.getPost().getUserId(), post.getPost().getComment()));
                        try {
                            commentsAdapter.notifyItemRangeChanged(0, commentsAdapter.getItemCount());
                            commentsAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                        }
                    }
                }
                @Override
                public void onFailure(Call<Comments> call, Throwable t) {}
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapterAndGetRecyclerView(){
        commentsAdapter = new CommentsAdapter(commentsList, this, commentsViewPresenter);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

    @Override
    public void startUserProfileActivity(String userId) {
//        startActivity(new Intent(this, ProfileActivity.class).putExtra("userid", userId));
    }

    @Override
    public void refreshView() {
        getComments(bundle.getString("postid"));
        editTextAddComment.setText("");
        setAdapterAndGetRecyclerView();
        commentsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getComments(bundle.getString("postid"));
        swipeRefreshLayout.setRefreshing(false);
        commentsAdapter.notifyDataSetChanged();
    }

}
