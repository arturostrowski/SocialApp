
package pl.almestinio.socialapp.http.userphoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersPic {

    @SerializedName("UserPic")
    @Expose
    private UserPic userPic;

    public UserPic getUserPic() {
        return userPic;
    }

    public void setUserPic(UserPic userPic) {
        this.userPic = userPic;
    }

}
