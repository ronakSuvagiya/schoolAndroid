package com.apps.smartschoolmanagement.retrofit;

import com.apps.smartschoolmanagement.model.loginModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {

    @POST("/posts")
    @FormUrlEncoded
    Call<loginModel> savePost(@Field("userName") String userName,
                        @Field("pass") String pass);
}
