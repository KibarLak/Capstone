package com.example.drpotato.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://34.121.60.115/";
    private static ApiService apiService;
    public static ApiService getClient() {
        if (apiService == null) {

            OkHttpClient okClient = new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(chain -> chain.proceed(chain.request()))
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = client.create(ApiService.class);
        }
        return apiService ;
    }
}
