package pl.almestinio.socialapp.http;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.almestinio.socialapp.http.comment.Comments;
import pl.almestinio.socialapp.http.post.Posts;
import pl.almestinio.socialapp.http.poststatus.PostStatus;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphotocover.UserPhotoCover;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by mesti193 on 3/7/2018.
 */

public interface RequestsService {

    @GET("adduser.php")
    Call<Users> addUser(@Query("name") String name,
                        @Query("email") String username,
                        @Query("password") String password);
    @GET("test.php")
    Call<Posts> requestPosts(@Query("allposts") String posts);
    @GET("test.php")
    Call<Posts> requestPostsUser(@Query("post") String post);
    @GET("test.php")
    Call<Users> requestUser(@Query("user") String id);
    @GET("test.php")
    Call<Users> requestUsers();
    @GET("test.php")
    Call<Users> requestUsers2(@Query("users") String user_name);
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

    @GET("insert.php")
    Call<Pojodemo> likePost(@Query("post_id") String post_id,
                            @Query("user_id") String user_id);

    @GET("delete.php")
    Call<Pojodemo> deleteLike(@Query("post_id") String post_id,
                              @Query("user_id") String user_id);
    @GET("deletecomment.php")
    Call<Pojodemo> deleteComment(@Query("comment_id") String comment_id);

    @GET("createpost.php")
    Call<Pojodemo> createPost(@Query("user_id") String user_id,
                              @Query("post_txt") String post_txt,
                              @Query("post_pic") String post_pic,
                              @Query("post_time") String post_time,
                              @Query("priority") String priority);
    @Multipart
    @POST("postpic.php")
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);


    @GET("changepic.php")
    Call<UserPhoto> changeUserPhoto(@Query("user_pic_edit") String user_id,
                                    @Query("user_pic") String user_pic);
    @GET("changepiccover.php")
    Call<UserPhoto> changeUserPhotoCover(@Query("user_cover_pic") String user_id,
                                         @Query("user_pic") String user_pic);
}
