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
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.User;
import pl.almestinio.socialapp.http.user.User_;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        friendsViewPresenter.loadSearchFriendsView(bundle.getString("users"));

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchUsers(String users) {
        userList.clear();
        try {
            RestClient.getClient().requestUsers2(users).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    for(User users : response.body().getUsers()){
                        userList.add(new User_(users.getUser().getUserId(), users.getUser().getName()));
                    }
                    try{
                        searchFriendAdapter.notifyItemRangeChanged(0, userList.size());
                        searchFriendAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {}
            });
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
