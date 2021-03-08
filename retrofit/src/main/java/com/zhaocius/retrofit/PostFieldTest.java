package com.zhaocius.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PostFieldTest {

    private static final String BASE_URL = "http://localhost:8080/test/";


    //http://localhost:8080/test/{userID}/poseField
    public interface PostField{
        @FormUrlEncoded                 //表单
        @POST("{userID}/poseField")
        Call<String>postField(@Path("userID") int userID, @Field("reason") String reason);
    }

    public void testPostField(){
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostField postField = retrofit.create(PostField.class);
        Call<String>request = postField.postField(1,"test");
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


}
