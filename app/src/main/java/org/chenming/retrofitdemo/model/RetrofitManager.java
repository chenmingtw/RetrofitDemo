package org.chenming.retrofitdemo.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String TARGET_URL = "https://jsonplaceholder.typicode.com/";

    private static RetrofitManager mInstance = new RetrofitManager();
    private MyAPIService myAPIService;

    private RetrofitManager() {
        // 設置baseUrl即要連的網站，addConverterFactory用Gson作為資料處理Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TARGET_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myAPIService = retrofit.create(MyAPIService.class);
    }

    public static RetrofitManager getInstance() {
        return mInstance;
    }

    public MyAPIService getMyAPIService() {
        return myAPIService;
    }
}
