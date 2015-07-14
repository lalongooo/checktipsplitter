package com.checktipsplitter.rest.permutassep;

import com.checktipsplitter.model.AuthModel;
import com.checktipsplitter.model.ConfirmPasswordReset;
import com.checktipsplitter.model.Email;
import com.checktipsplitter.model.Post;
import com.checktipsplitter.model.PostPage;
import com.checktipsplitter.model.User;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface  IPermutasSEPService {

    @POST("/users/")
    void newUser(@Body User user, Callback<User> callback);

    @GET("/users/{id}/posts")
    void myPosts(@Path("id") int id, Callback<List<Post>> callback);

    @POST("/posts/")
    void newPost(@Body Post post, Callback<Post> callback);

    @GET("/posts/")
    void searchPosts(@QueryMap Map<String, String> parameters, Callback<List<Post>> callback);

    @GET("/posts/")
    void getPostPage(@Query("page") int page, @Query("page_size") int pageSize, Callback<PostPage> callback);

    @POST("/login/")
    void login(@Body AuthModel authModel, Callback<User> callback);

    @PATCH("/users/{id}")
    void updateUser(@Path("id") int id, @Body User user, Callback<User> callback);

    @POST("/reset-password/")
    void resetPassword(@Body Email email, ResponseCallback responseCallback);

    @POST("/confirm-reset-password/")
    void confirmPasswordReset(@Body ConfirmPasswordReset cpr, ResponseCallback responseCallback);
}