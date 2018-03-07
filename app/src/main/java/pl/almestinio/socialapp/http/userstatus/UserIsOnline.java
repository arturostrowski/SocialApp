
package pl.almestinio.socialapp.http.userstatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserIsOnline {

    @SerializedName("UserStatus")
    @Expose
    private UserStatus_ userStatus;

    public UserStatus_ getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus_ userStatus) {
        this.userStatus = userStatus;
    }

}
