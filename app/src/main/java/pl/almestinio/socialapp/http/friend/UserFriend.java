
package pl.almestinio.socialapp.http.friend;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFriend {

    @SerializedName("Friends")
    @Expose
    private List<Friend> friends = null;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

}
