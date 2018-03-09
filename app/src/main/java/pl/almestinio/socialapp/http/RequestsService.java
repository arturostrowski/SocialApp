package pl.almestinio.socialapp.http;

import pl.almestinio.socialapp.http.comment.Comments;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.http.poststatus.PostStatus;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphotocover.UserPhotoCover;
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
    @GET("test.php")
    Call<UserPhoto> requestUserPhoto(@Query("user_pic") String id);
    @GET("test.php")
    Call<UserPhotoCover> requestUserPhotoCover(@Query("user_cover_pic") String id);
    @GET("test.php")
    Call<PostStatus> requestStatus(@Query("post_status") String id);
    @GET("test.php")
    Call<Comments> requestComments(@Query("post_comments") String id);


    @GET("deletepost.php")
    Call<Pojodemo> deletePost(@Query("post_id") String post_id);



    @GET("writecomment.php")
    Call<Pojodemo> writeComment(@Query("post_id") String post_id,
                                @Query("user_id") String user_id,
                                @Query("comment") String comment);

}
