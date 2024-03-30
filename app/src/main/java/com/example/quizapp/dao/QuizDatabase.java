package com.example.quizapp.dao;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizapp.model.UserModel;



@Database(entities = {UserModel.class}, version = 2)
public abstract class QuizDatabase extends RoomDatabase {
    private static QuizDatabase mInstance;

    public QuizDao quizDao;

    public static synchronized QuizDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), QuizDatabase.class,
                            "USER_DB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return mInstance;
    }


    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("PRAGMA encoding='UTF-8';");
        }
    };
}

