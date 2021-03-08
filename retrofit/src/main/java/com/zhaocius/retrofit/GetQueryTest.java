package com.zhaocius.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class GetQueryTest {

    private static final String BASE_URL = "http://localhost:8080/test/";


    //http://localhost:8080/test/getQuery?pageIndex=0&pageSize=8
    public interface GetQuery{
        @GET("getQuery")
        Call<String>getQuery(@Query("pageIndex")int pageIndex,@Query("pageSize")int pageSize);
    }

    public void testGetQuery(){
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetQuery request = retrofit.create(GetQuery.class);
        Call<String> call = request.getQuery(1,8);
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
