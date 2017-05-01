package com.example.sashok.task_;

import android.app.Application;

import com.example.sashok.task_.Answer.Answer;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sashok on 30.4.17.
 */

public class MyApplication extends Application {

    private static ServiceApi serviceApi;
    private static MyApplication mInstance;
    private Retrofit retrofit;

    public static ServiceApi getServiceApi() {
        return serviceApi;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://someservice.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceApi = retrofit.create(ServiceApi.class);
    }

    interface ServiceApi {
        @POST("login")
        Call<Answer> Authenticate(@Query("login") String login, @Query("password") String password);

        @POST("registration")
        Call<Answer> Registration(@Query("login") String login, @Query("password") String password, @Query("phone") String phone);

        @GET("getAllCategories")
        Call<Answer> GetAllCategories();
    }
}
