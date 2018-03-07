
package pl.almestinio.socialapp.http.userphotocover;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPhotoCover {

    @SerializedName("UsersPic")
    @Expose
    private List<UsersPic> usersPic = null;

    public List<UsersPic> getUsersPic() {
        return usersPic;
    }

    public void setUsersPic(List<UsersPic> usersPic) {
        this.usersPic = usersPic;
    }

}
