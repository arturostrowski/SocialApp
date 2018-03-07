
package pl.almestinio.socialapp.http.userinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo_ {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("school_or_collage")
    @Expose
    private String schoolOrCollage;
    @SerializedName("current_city")
    @Expose
    private String currentCity;
    @SerializedName("hometown")
    @Expose
    private String hometown;
    @SerializedName("relationship_status")
    @Expose
    private String relationshipStatus;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("mobile_no_priority")
    @Expose
    private String mobileNoPriority;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("Facebook_ID")
    @Expose
    private String facebookID;

    private boolean isInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchoolOrCollage() {
        return schoolOrCollage;
    }

    public void setSchoolOrCollage(String schoolOrCollage) {
        this.schoolOrCollage = schoolOrCollage;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNoPriority() {
        return mobileNoPriority;
    }

    public void setMobileNoPriority(String mobileNoPriority) {
        this.mobileNoPriority = mobileNoPriority;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }


    public boolean isInfo(){

        if(getJob().isEmpty()&&getSchoolOrCollage().isEmpty()&&getCurrentCity().isEmpty()&&getHometown().isEmpty()&&getRelationshipStatus().isEmpty()&&getMobileNo().isEmpty()&&getWebsite().isEmpty()&&getFacebookID().isEmpty()){
            isInfo=false;
        }else{
            isInfo=true;
        }

        return isInfo;
    }

}
