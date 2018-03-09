package pl.almestinio.socialapp.model;

import android.databinding.BaseObservable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by mesti193 on 3/7/2018.
 */

@DatabaseTable(tableName = "User")
public class User extends BaseObservable implements Serializable {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "userid";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private int id;
    @DatabaseField(columnName = COL_NAME)
    private static String userId;

    public User(){

    }

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
