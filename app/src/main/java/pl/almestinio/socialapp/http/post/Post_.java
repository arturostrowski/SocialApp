
package pl.almestinio.socialapp.http.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post_ {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("post_txt")
    @Expose
    private String postTxt;
    @SerializedName("post_pic")
    @Expose
    private String postPic;
    @SerializedName("post_time")
    @Expose
    private String postTime;
    @SerializedName("priority")
    @Expose
    private String priority;

    public Post_(String postId, String userId, String postTxt, String postPic, String postTime, String priority) {
        this.postId = postId;
        this.userId = userId;
        this.postTxt = postTxt;
        this.postPic = postPic;
        this.postTime = postTime;
        this.priority = priority;
    }

    public Post_(String postTxt){
        this.postTxt = postTxt;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostTxt() {
        return postTxt;
    }

    public void setPostTxt(String postTxt) {
        this.postTxt = postTxt;
    }

    public String getPostPic() {
        return postPic;
    }

    public void setPostPic(String postPic) {
        this.postPic = postPic;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
