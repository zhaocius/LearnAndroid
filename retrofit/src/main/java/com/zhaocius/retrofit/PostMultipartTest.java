package com.zhaocius.retrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class PostMultipartTest {

    private static final String BASE_URL = "http://localhost:8080/test/";


    //http://localhost:8080/test/postMultipart
    public interface PostMultipart{
        @Multipart                 //文件
        @POST("postMultipart")
        Call<String>PostMultipart(@Part MultipartBody.Part photo, @Part MultipartBody.Part word);
    }

    public void testPostField(){
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(BASE_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostMultipart postMultipart = retrofit.create(PostMultipart.class);


        File file1 = null;
        try{
            file1 = new File("/sdcard/photo");
        }catch (Exception e){

        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file1);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",file1.getName(),requestBody);

        File file2 = null;
        try{
            file2 = new File("/sdcard/word");
        }catch (Exception e){

        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"),file2);
        MultipartBody.Part word = MultipartBody.Part.createFormData("word",file2.getName(),requestBody1);

        Call<String> request = postMultipart.PostMultipart(photo,word);

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
