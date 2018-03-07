package pl.almestinio.socialapp.model;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class User {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "userid";

    private int id;
    private String userId;

    public User(String userId){
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
