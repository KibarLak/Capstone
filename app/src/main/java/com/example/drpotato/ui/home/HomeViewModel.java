package com.example.drpotato.ui.home;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drpotato.model.Potato;
import com.example.drpotato.network.ApiClient;
import com.example.drpotato.network.ApiService;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Potato> potatoMutableLiveData;
    private MutableLiveData<Bitmap> image;
    private MutableLiveData<List<Potato>> potatoes;
    private ApiService apiService = ApiClient.getClient();

    public HomeViewModel() {
        potatoMutableLiveData = null;
        potatoes = null;
    }

    public LiveData<Potato> getPotato(MultipartBody.Part file){
        potatoMutableLiveData = new MutableLiveData<>();
        getPredictFromApi(file);
        return potatoMutableLiveData;
    }

    public MutableLiveData<Potato> getCurrentPotato(){
        return potatoMutableLiveData;
    }

    private void getPredictFromApi(MultipartBody.Part file){
        Call<Potato> call = apiService.predictDisease(file);
        call.enqueue(new Callback<Potato>() {
            @Override
            public void onResponse(Call<Potato> call, Response<Potato> response) {
                potatoMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Potato> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}