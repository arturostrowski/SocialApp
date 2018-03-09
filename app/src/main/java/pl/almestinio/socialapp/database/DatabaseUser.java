package pl.almestinio.socialapp.database;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.almestinio.socialapp.model.User;


/**
 * Created by mesti193 on 3/10/2018.
 */

public class DatabaseUser {

    public static List<User> getCategories() {
        List<User> categoryList = null;
        try {
            categoryList = DatabaseHelper.instance.getUser().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static void addOrUpdateCategories(User user) {
        try {
            DatabaseHelper.instance.getUser().createOrUpdate(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCategories(String user) {
        try {
//            DatabaseHelper.instance.getUser().update(user);
            UpdateBuilder<User, Integer> updateBuilder = DatabaseHelper.instance.getUser().updateBuilder();
            updateBuilder.where().eq("id", 1);
            updateBuilder.updateColumnValue("userid", user);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllCategories() {
        try {
            DeleteBuilder<User, Integer> deleteBuilder = DatabaseHelper.instance.getUser().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
