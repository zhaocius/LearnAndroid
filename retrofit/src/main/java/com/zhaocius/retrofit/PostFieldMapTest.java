package com.zhaocius.retrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class PostFieldMapTest {

    private static final String BASE_URL = "http://localhost:8080/test/";


    //http://localhost:8080/test/poseFieldMap
    public interface PostFieldMap{
        @FormUrlEncoded                 //表单
        @POST("poseFieldMap")
        Call<String>postFieldMap(@FieldMap Map<String, Object> reason);
    }

    public void testPostFiled(){
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostFieldMap postFieldMap = retrofit.create(PostFieldMap.class);
        Map<String,Object>maps = new HashMap<>();
        maps.put("test",1);
        maps.put("test2",3);
        Call<String>request = postFieldMap.postFieldMap(maps);
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
