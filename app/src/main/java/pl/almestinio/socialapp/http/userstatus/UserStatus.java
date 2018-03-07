
package pl.almestinio.socialapp.http.userstatus;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStatus {

    @SerializedName("UserIsOnline")
    @Expose
    private List<UserIsOnline> userIsOnline = null;

    public List<UserIsOnline> getUserIsOnline() {
        return userIsOnline;
    }

    public void setUserIsOnline(List<UserIsOnline> userIsOnline) {
        this.userIsOnline = userIsOnline;
    }

}
