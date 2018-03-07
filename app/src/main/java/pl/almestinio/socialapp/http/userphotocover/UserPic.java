
package pl.almestinio.socialapp.http.userphotocover;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPic {

    @SerializedName("cover_id")
    @Expose
    private String coverId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String image;

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
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
