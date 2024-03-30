package com.example.quizapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizapp.model.QuestionResponse;
import com.example.quizapp.network.RetroRepository;
import com.example.quizapp.network.RetroServiceInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class QuizVM extends ViewModel {

    MutableLiveData<QuestionResponse> responseMutableLiveData;

    @Inject
    RetroServiceInterface retroServiceInterface;



    @Inject
    public QuizVM() {
        responseMutableLiveData = new MutableLiveData();
    }

//    public void insertUser(UserModel user) {
//        repository.insertUser(user);
//    }


    public MutableLiveData<QuestionResponse> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public void makeApi(int amt, String type) {
        RetroRepository retroRepository = new RetroRepository(retroServiceInterface);
        retroRepository.makeCALL(amt, type, responseMutableLiveData);
    }


//    public LiveData<List<UserModel>> getUsers() {
//        return repository.getUsers();
//    }


}
