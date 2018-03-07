
package pl.almestinio.socialapp.http.userphoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPic {

    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String image;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
