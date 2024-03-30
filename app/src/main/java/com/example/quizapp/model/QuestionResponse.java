package com.example.quizapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionResponse {
    @SerializedName("response_code")
    public int response_code;
    @SerializedName("results")
    public ArrayList<ResultData> results;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public ArrayList<ResultData> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultData> results) {
        this.results = results;
    }



}
