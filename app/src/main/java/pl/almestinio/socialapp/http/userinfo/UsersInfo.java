
package pl.almestinio.socialapp.http.userinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersInfo {

    @SerializedName("UserInfo")
    @Expose
    private UserInfo_ userInfo;

    public UserInfo_ getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo_ userInfo) {
        this.userInfo = userInfo;
    }

}
