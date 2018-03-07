
package pl.almestinio.socialapp.http.userinfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("UsersInfo")
    @Expose
    private List<UsersInfo> usersInfo = null;

    public List<UsersInfo> getUsersInfo() {
        return usersInfo;
    }

    public void setUsersInfo(List<UsersInfo> usersInfo) {
        this.usersInfo = usersInfo;
    }

}
