package pl.almestinio.socialapp.http;

import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.http.user.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface RequestsService {

    @GET("test.php")
    Call<Posts> requestPosts(@Query("allposts") String posts);
    @GET("test.php")
    Call<Posts> requestPostsUser(@Query("post") String post);
    @GET("test.php")
    Call<Users> requestUser(@Query("user") String id);

}
