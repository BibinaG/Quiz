package com.example.quizapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.quizapp.model.UserModel;


@Dao
public interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIntoUser(UserModel userData);

    @Query(value = "SELECT * FROM User")
    LiveData<UserModel> getAllUserDATA();
}
