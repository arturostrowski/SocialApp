package pl.almestinio.socialapp.ui.searchFriendsView;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class SearchFriendsViewPresenter implements SearchFriendsViewContracts.FriendsViewPresenter {

    private SearchFriendsViewContracts.FriendsView friendsView;

    public SearchFriendsViewPresenter(SearchFriendsViewContracts.FriendsView friendsView){
        this.friendsView = friendsView;
    }

    @Override
    public void loadSearchFriendsView(String users) {
        friendsView.showSearchUsers(users);
        friendsView.setAdapterAndGetRecyclerView();
    }

    @Override
    public void onClickUser(String userId) {
        friendsView.startProfiledActivity(userId);
    }
}
