package com.example.quizapp.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.dao.QuizDatabase;
import com.example.quizapp.databinding.FragmentQuizBinding;
import com.example.quizapp.model.ResultData;
import com.example.quizapp.model.UserModel;
import com.example.quizapp.viewmodel.QuizVM;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuizFragment extends Fragment implements View.OnClickListener {
    FragmentQuizBinding fragmentQuizBinding;
    QuizVM quizVM;
    int mSelectedOptionPosition = 0;
    ArrayList<ResultData> mQuestionsList = new ArrayList<>();
    List<String> allOptions;
    int mCurrentPosition = 1;
    int mCorrectAnswers = 0;

    boolean timesUp = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQuizBinding = FragmentQuizBinding.inflate(getLayoutInflater());
        return fragmentQuizBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initViewModel();
        quizVM.makeApi(10, "multiple");
        observeViewModel();
        startCountDown();

    }

    private void observeViewModel() {
        quizVM.getResponseMutableLiveData().observe(this, questionResponse -> {
            if (questionResponse.getResults() != null) {
                fragmentQuizBinding.progressbar.setVisibility(View.GONE);
                for (ResultData reposeModel : questionResponse.getResults()) {
                    fragmentQuizBinding.tvQuestion.setText(reposeModel.question);
                    mQuestionsList = questionResponse.getResults();
                    setQuestion();

                }
            } else {
                fragmentQuizBinding.progressbar.setVisibility(View.GONE);
            }
        });
    }


    private void initViewModel() {
        quizVM = new ViewModelProvider(this).get(QuizVM.class);


    }

    private void initView() {
        fragmentQuizBinding.tvName.setText("Hello " + WelcomeFragment.loginUserName);
        fragmentQuizBinding.tvOptionOne.setOnClickListener(this);
        fragmentQuizBinding.tvOptionTwo.setOnClickListener(this);
        fragmentQuizBinding.tvOptionThree.setOnClickListener(this);
        fragmentQuizBinding.tvOptionFour.setOnClickListener(this);
        fragmentQuizBinding.btnSubmit.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOptionOne:
                selectedOptionView(fragmentQuizBinding.tvOptionOne, 1);
                break;
            case R.id.tvOptionTwo:
                selectedOptionView(fragmentQuizBinding.tvOptionTwo, 2);
                break;
            case R.id.tvOptionThree:
                selectedOptionView(fragmentQuizBinding.tvOptionThree, 3);
                break;
            case R.id.tvOptionFour:
                selectedOptionView(fragmentQuizBinding.tvOptionFour, 4);
                break;
            case R.id.btnSubmit:
//                if (!timesUp) {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++;
                    if (mCurrentPosition <= mQuestionsList.size()) {
                        setQuestion();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", WelcomeFragment.loginUserName);
                        bundle.putInt("mCorrectAnswers", mCorrectAnswers);
                        bundle.putInt("totalQuestion", mQuestionsList.size());
                        Navigation.findNavController(v).navigate(R.id.action_quizFragment_to_resultFragment, bundle);

                    }

                } else {
                    ResultData question = mQuestionsList.get(mCurrentPosition - 1);
                    int correctOptionPosition = allOptions.indexOf(question.getCorrect_answer()) + 1;
                    if (correctOptionPosition != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border);
                    } else {
                        mCorrectAnswers++;
                    }
                    answerView(correctOptionPosition, R.drawable.correct_option_border);

                    if (mCurrentPosition == mQuestionsList.size()) {
                        fragmentQuizBinding.btnSubmit.setText(" FINISH");
                    } else {
                        fragmentQuizBinding.btnSubmit.setText("GO TO NEXT QUESTION");
                    }
                    mSelectedOptionPosition = 0;
                }
                break;
        }

    }

    private void setQuestion() {
        ResultData question = mQuestionsList.get(mCurrentPosition - 1);

        defaultOptionView();

        if (mCurrentPosition == mQuestionsList.size()) {
            fragmentQuizBinding.btnSubmit.setText("FINISH");
        } else {
            fragmentQuizBinding.btnSubmit.setText("SUBMIT");
        }

        fragmentQuizBinding.progressBar.setProgress(mCurrentPosition);
        fragmentQuizBinding.tvProgress.setText(mCurrentPosition + "/" + fragmentQuizBinding.progressBar.getMax());

        fragmentQuizBinding.tvQuestion.setText(question.getQuestion());
        allOptions = new ArrayList<>(question.getIncorrect_answers());
        allOptions.add(question.getCorrect_answer());
        Collections.shuffle(allOptions);
        fragmentQuizBinding.tvOptionOne.setText(allOptions.get(0));
        fragmentQuizBinding.tvOptionTwo.setText(allOptions.get(1));
        fragmentQuizBinding.tvOptionThree.setText(allOptions.get(2));
        fragmentQuizBinding.tvOptionFour.setText(allOptions.get(3));
    }

    private void defaultOptionView() {
        List<TextView> options = new ArrayList<>();
        options.add(0, fragmentQuizBinding.tvOptionOne);
        options.add(1, fragmentQuizBinding.tvOptionTwo);
        options.add(2, fragmentQuizBinding.tvOptionThree);
        options.add(3, fragmentQuizBinding.tvOptionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.options_text_view_bg));
        }
    }

    private void answerView(int answer, int drawableView) {
        switch (answer) {
            case 1:
                fragmentQuizBinding.tvOptionOne.setBackground(ContextCompat.getDrawable(requireContext(), drawableView));
                break;
            case 2:
                fragmentQuizBinding.tvOptionTwo.setBackground(ContextCompat.getDrawable(requireContext(), drawableView));
                break;
            case 3:
                fragmentQuizBinding.tvOptionThree.setBackground(ContextCompat.getDrawable(requireContext(), drawableView));
                break;
            case 4:
                fragmentQuizBinding.tvOptionFour.setBackground(ContextCompat.getDrawable(requireContext(), drawableView));
                break;
        }
    }

    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionView();
        mSelectedOptionPosition = selectedOptionNum;
        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_options_text_view_bg));
    }

    private void startCountDown() {
        new CountDownTimer(119000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                fragmentQuizBinding.tvCounter.setText(minutes + " : " + seconds);
            }

            @Override
            public void onFinish() {
                fragmentQuizBinding.tvCounter.setText("Times Up.");
                fragmentQuizBinding.btnSubmit.setText(" FINISH");
                mSelectedOptionPosition = 0;
                timesUp = true;

            }
        };
    }
}