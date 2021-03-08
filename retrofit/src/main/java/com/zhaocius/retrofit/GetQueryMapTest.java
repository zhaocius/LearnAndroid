package com.zhaocius.retrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class GetQueryMapTest {

    private static final String BASE_URL = "http://localhost:8080/test/";

    //http://localhost:8080/test/getQuery?pageIndex=0&pageSize=8
    public interface GetQueryMap{
        @GET("getQuery")
        Call<String> getQueryMap(@QueryMap Map<String,String> map);
    }

    public void testGetQueryMap(){
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetQueryMap request = retrofit.create(GetQueryMap.class);
        Map<String,String> map = new HashMap<>();
        map.put("pageIndex","1");
        map.put("pageSize","8");
        Call<String> call = request.getQueryMap(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }


}
