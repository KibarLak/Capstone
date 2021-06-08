package com.example.drpotato.ui.disease;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drpotato.model.Potato;
import com.example.drpotato.network.ApiClient;
import com.example.drpotato.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseaseViewModel extends ViewModel {

    private MutableLiveData<List<Potato>> potatoes;
    private MutableLiveData<Potato> potato;
    private ApiService apiService = ApiClient.getClient();

    public LiveData<List<Potato>> getDiseases(){
        potatoes = new MutableLiveData<>();
        getDiseasesFromApi();
        return potatoes;
    }

    private void getDiseasesFromApi(){
        Call<List<Potato>> call = apiService.getDiseases();
        call.enqueue(new Callback<List<Potato>>() {
            @Override
            public void onResponse(Call<List<Potato>> call, Response<List<Potato>> response) {
                potatoes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Potato>> call, Throwable t) {

            }
        });

    }

    public LiveData<Potato> getPotato() {
        return potato;
    }

    public void setPotato(Potato potato) {
        this.potato = new MutableLiveData<>();
        this.potato.setValue(potato);
    }
}