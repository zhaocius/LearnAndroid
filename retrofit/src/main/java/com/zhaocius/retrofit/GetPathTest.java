package com.zhaocius.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GetPathTest {

    private static final String BASE_URL = "http://localhost:8080/test/";


    //http://localhost:8080/test/{userID}/getPath
    public interface GetPath{
        @GET("{userID}/getPath")
        Call<String> getPath(@Path("userID")int userID);
    }

    public void testGetPath(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        GetPath request = retrofit.create(GetPath.class);

        Call<String> call = request.getPath(6);
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
