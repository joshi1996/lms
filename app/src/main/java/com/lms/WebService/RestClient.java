package com.lms.WebService;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lms.View.LoginActivity;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static Retrofit getClient(final Context mcontext) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(40, TimeUnit.SECONDS);

    httpClient.addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
           /* if(SharePrefs.getUserdetail(mcontext)!=null && SharePrefs.getUserdetail(mcontext).getToken()!=null)
            {*/


            Request request;
            if (SharePrefs.getUserdetail(mcontext) != null) {
                request= chain.request().newBuilder().addHeader("Authorization",SharePrefs.getUserdetail(mcontext).getAuthtoken()).addHeader("x-api-key","c0fa1bc00534b69726b6d616e20000000722227335444556666c657321a516ea6ea959d6658e")
                        .build();




            }
           else{
                request= chain.request().newBuilder().addHeader("x-api-key","c0fa1bc00534b69726b6d616e20000000722227335444556666c657321a516ea6ea959d6658e")
                        .build();

            }



            Response response = chain.proceed(request);


            return response;

        }
    });

        Retrofit mretrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();


        return mretrofit;
    }




}
