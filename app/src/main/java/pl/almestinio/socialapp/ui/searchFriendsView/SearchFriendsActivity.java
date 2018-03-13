package pl.almestinio.socialapp.ui.searchFriendsView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.SearchFriendsAdapter;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.User_;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class SearchFriendsActivity extends AppCompatActivity implements SearchFriendsViewContracts.FriendsView{

    private SearchFriendsAdapter searchFriendAdapter;
    private List<User_> userList = new ArrayList<User_>();
    private RecyclerView recyclerView;
    private Bundle bundle;

    private SearchFriendsViewContracts.FriendsViewPresenter friendsViewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        bundle = getIntent().getExtras();

        friendsViewPresenter = new SearchFriendsViewPresenter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchFriends);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        friendsViewPresenter.getUsers(bundle.getString("users"));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchUsers(List<User> usersList) {
        userList.clear();
        try{
            for(User users : usersList){
                userList.add(new User_(users.getUser().getUserId(), users.getUser().getName()));
            }
            searchFriendAdapter.notifyItemRangeChanged(0, userList.size());
            searchFriendAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapterAndGetRecyclerView() {
        searchFriendAdapter = new SearchFriendsAdapter(userList, this, friendsViewPresenter);
        recyclerView.setAdapter(searchFriendAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.invalidate();
    }

    @Override
    public void startProfiledActivity(String userId) {
        startActivity(new Intent(this, ProfileActivity.class).putExtra("userid", userId));
    }
}
