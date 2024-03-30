package com.example.quizapp.network;

import com.example.quizapp.model.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServiceInterface {
    @GET("api.php")
    Call<QuestionResponse> getQuestions(@Query("amount") int amount, @Query("type") String type);

}
