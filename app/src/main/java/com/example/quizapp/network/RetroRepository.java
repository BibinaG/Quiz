package com.example.quizapp.network;

import androidx.lifecycle.MutableLiveData;

import com.example.quizapp.model.QuestionResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RetroRepository {
    private RetroServiceInterface retroServiceInterface;


    public RetroRepository(RetroServiceInterface retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void makeCALL(int amount, String type, MutableLiveData<QuestionResponse> liveData) {
        Call<QuestionResponse> call = retroServiceInterface.getQuestions(amount, type);
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {

            }
        });
    }
}
