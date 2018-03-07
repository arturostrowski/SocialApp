package pl.almestinio.socialapp.model;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class User {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "userid";

    private int id;
    private static String userId = "10";

    public User(String userId){
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        User.userId = userId;
    }
}
