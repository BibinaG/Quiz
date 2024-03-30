//package com.example.quizapp.di;
//
//import android.app.Application;
//
//import androidx.room.Room;
//
//import com.example.quizapp.dao.QuizDao;
//import com.example.quizapp.dao.QuizDatabase;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import dagger.hilt.InstallIn;
//import dagger.hilt.components.SingletonComponent;
//
//@Module
//@InstallIn(SingletonComponent.class)
//public abstract class DatabaseModule {
//
//    @Singleton
//    @Provides
//    public static QuizDatabase provideQuizDatabase(Application application) {
//        return Room.databaseBuilder(application, QuizDatabase.class, "quiz_database")
//                .build();
//    }
//
//    @Singleton
//    @Provides
//    public static QuizDao provideQuizDao(QuizDatabase quizDatabase) {
//        return quizDatabase.quizDao;
//    }
//}
//
