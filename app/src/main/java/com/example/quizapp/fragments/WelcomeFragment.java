package com.example.quizapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.databinding.FragmentWelcomeBinding;


public class WelcomeFragment extends Fragment {
    FragmentWelcomeBinding welcomeBinding;

    public static String loginUserName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        welcomeBinding = FragmentWelcomeBinding.inflate(getLayoutInflater());
        return welcomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        welcomeBinding.btnStart.setOnClickListener(view1 -> {
            if (welcomeBinding.etName.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Name Field cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                loginUserName = welcomeBinding.etName.getText().toString();
                Navigation.findNavController(view1).navigate(R.id.action_welcomeFragment_to_quizFragment);
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        welcomeBinding = null;
    }
}