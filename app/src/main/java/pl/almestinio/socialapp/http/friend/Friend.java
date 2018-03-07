
package pl.almestinio.socialapp.http.friend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("Friend")
    @Expose
    private Friend_ friend;

    public Friend_ getFriend() {
        return friend;
    }

    public void setFriend(Friend_ friend) {
        this.friend = friend;
    }

}
