
package pl.almestinio.socialapp.http.friend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend_ {

    @SerializedName("relationship_id")
    @Expose
    private String relationshipId;
    @SerializedName("user_one_id")
    @Expose
    private String userOneId;
    @SerializedName("user_two_id")
    @Expose
    private String userTwoId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("action_user_id")
    @Expose
    private String actionUserId;

    public Friend_(String relationshipId, String userOneId, String userTwoId, String status, String actionUserId) {
        this.relationshipId = relationshipId;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.status = status;
        this.actionUserId = actionUserId;
    }

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(String userOneId) {
        this.userOneId = userOneId;
    }

    public String getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(String userTwoId) {
        this.userTwoId = userTwoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(String actionUserId) {
        this.actionUserId = actionUserId;
    }

}
