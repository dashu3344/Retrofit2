package com.yztc.retrofit2;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by wanggang on 2017/1/13.
 */

public interface IApi {


         @FormUrlEncoded   //对 请求中的参数进行编码
     @POST("RegServlet")//POST 请求
     Call<ResponseBody> register(@Field("username") String user,
                                 @Field("password") String password );// @Field("username")  key   user  values



     @FormUrlEncoded   //对 请求中的参数进行编码
     @POST("RegServlet")//POST 请求
     Call<ResponseBody> register1(@FieldMap HashMap<String,String> params);//@FieldMap  传递多个参数


     @POST("JsonHandler")
     Call<ResponseBody> postJson(@Body JSONObject object);


     @POST("JsonHandler")
     Call<BaseResult> getJson(@Body JSONObject object);


     @POST("JsonHandler")
     Call<BaseResult<String>> getJsonString(@Body JSONObject object);


     @GET("RegServlet")
     Call<String> getResult(@Query("username") String username,
                            @Query("password") String  password);


     @GET("RegServlet")
     Call<String> getResult2(@QueryMap HashMap<String,String> params);



     @GET
     Call<String> getResult2(@Url String url);  //不会与baseUrl 进行拼接

     @Streaming
     @GET
     Call<ResponseBody> down(@Url String url);  //不会与baseUrl 进行拼接

}
