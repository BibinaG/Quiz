package com.example.quizapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp.R;
import com.example.quizapp.dao.QuizDatabase;
import com.example.quizapp.databinding.FragmentResultBinding;
import com.example.quizapp.viewmodel.QuizVM;
import com.google.gson.Gson;


public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    private NavController navController;
    QuizVM quizVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        intiViews();

    }

    private void initViewModel() {
        quizVM = new ViewModelProvider(this).get(QuizVM.class);


    }

    private void intiViews() {
        String receivedValue = getArguments().getString("name");
        int correctAnswer = getArguments().getInt("mCorrectAnswers");
        int totalQuestion = getArguments().getInt("totalQuestion");
//        QuizDatabase.getInstance(requireContext()).quizDao.getAllUserDATA().observe(this, data -> {
//            Log.e("intiViews: ", new Gson().toJson(data));
//        });
        binding.tvName.setText(receivedValue);
        binding.tvScore.setText("Your Score is " + correctAnswer + " out of" + totalQuestion);


        binding.btnFinish.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_welcomeFragment);

        });

    }
}