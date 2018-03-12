package pl.almestinio.socialapp.ui.menuView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.adapters.SectionPagerAdapter;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.friend.UserFriend;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.createPostView.CreatePostActivity;
import pl.almestinio.socialapp.ui.menuInvitationsToFriendsView.InvitationsFragment;
import pl.almestinio.socialapp.ui.menuSettingsView.SettingsFragment;
import pl.almestinio.socialapp.ui.menuTimelineView.TimelineFragment;
import pl.almestinio.socialapp.ui.searchFriendsView.SearchFriendsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class MenuActivity extends AppCompatActivity implements MenuViewContracts.MenuView{

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private Menu menu;
    private SectionPagerAdapter sectionPagerAdapter;

    MenuViewContracts.MenuViewPresenter menuViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        init();
    }

    private void init(){
        menuViewPresenter = new MenuViewPresenter(this);

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        sectionPagerAdapter.addFragment(new TimelineFragment());
        sectionPagerAdapter.addFragment(new InvitationsFragment());
        sectionPagerAdapter.addFragment(new SettingsFragment());

        viewPager.setAdapter(sectionPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);




        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_posts);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_invitations);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_menu);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(2000);
//                        Log.i("th", "thread");
                        changeInvitationsTabLayout();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();



    }

    private void changeInvitationsTabLayout(){
        try{
            RestClient.getClient().requestNotConfirmedFriends(User.getUserId()).enqueue(new Callback<UserFriend>() {
                @Override
                public void onResponse(Call<UserFriend> call, Response<UserFriend> response) {
                    if(response.body().getFriends().isEmpty()){
                        Log.i("th", "empty");
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_invitations);
                    }else{
                        Log.i("th", "not empty");
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_invitations_plus);
                    }
                }
                @Override
                public void onFailure(Call<UserFriend> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);


        MenuItem menuItemAddPost = menu.findItem(R.id.action_add_new_post);
        menuItemAddPost.setVisible(true);
        menuItemAddPost.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menuViewPresenter.onAddPostMenuItemClick();
                return false;
            }
        });

        MenuItem menuItemSearch = menu.findItem(R.id.action_search);
        MaterialSearchView materialSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        materialSearchView.setVisibility(View.GONE);
        materialSearchView.setMenuItem(menuItemSearch);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                materialSearchView.setVisibility(View.VISIBLE);
                menuViewPresenter.onSearchFriendClick(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                /*

                    WSTAWIC TUTAJ RESTa ;>

                 */

                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                materialSearchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                materialSearchView.setVisibility(View.GONE);
            }
        });

        return true;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startCreatePostActivity() {
        startActivity(new Intent(this, CreatePostActivity.class));
    }

    @Override
    public void startSearchFriendActivity(String query) {
        startActivity(new Intent(this, SearchFriendsActivity.class).putExtra("users", query));
    }
}
