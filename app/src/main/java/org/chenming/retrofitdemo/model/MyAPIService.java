package org.chenming.retrofitdemo.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyAPIService {

    @GET("albums/1")            // 設置一個GET連線，路徑為albums/1
    Call<Albums> getAlbums();   // 取得的回傳資料用Albums物件接收，連線名稱取為getAlbums

    @GET("albums/{id}")         // 用{}表示路徑參數，@Path會將參數帶入至該位置
    Call<Albums> getAlbumsById(@Path("id") int id);

    @GET("posts/{id}")
    Call<Posts> getPostsById(@Path("id") int id);

    @GET("comments/{id}")
    Call<Comments> getCommentsById(@Path("id") int id);

    @GET("posts/{id}/comments")
    Call<List<Comments>> getCommentsByPostId(@Path("id") int id);

    @GET("comments")            // 用@Query表示要傳送Query資料(?xxx=yy)
    Call<List<Comments>> getCommentsByQueryPostId(@Query("postId") int id);

    @GET("photos/{id}")
    Call<Photos> getPhotosById(@Path("id") int id);

    @GET("todos/{id}")
    Call<Todos> getTodosById(@Path("id") int id);

    @POST("albums")             // 用@Body表示要傳送Body資料
    Call<Albums> postAlbums(@Body Albums albums);

    @POST("posts")              // 用@Body表示要傳送Body資料
    Call<Posts> postPosts(@Body Posts posts);

    @POST("comments")           // 用@Body表示要傳送Body資料
    Call<Comments> postComments(@Body Comments comments);

    @DELETE("albums/{id}")
    Call<Albums> delAlbumsById(@Path("id") int id);

    @DELETE("posts/{id}")
    Call<Posts> delPostsById(@Path("id") int id);

    @DELETE("comments/{id}")
    Call<Comments> delCommentsById(@Path("id") int id);

    @DELETE("photos/{id}")
    Call<Photos> delPhotosById(@Path("id") int id);

    @DELETE("todos/{id}")
    Call<Todos> delTodosById(@Path("id") int id);
}
