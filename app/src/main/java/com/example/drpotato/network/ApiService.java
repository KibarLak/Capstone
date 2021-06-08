package com.example.drpotato.network;

import com.example.drpotato.model.Potato;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("diseases")
    Call<List<Potato>> getDiseases();

    @POST("./")
    @Multipart
    Call<Potato> predictDisease(@Part MultipartBody.Part file);
}
