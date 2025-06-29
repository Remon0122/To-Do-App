package com.example.todo.database.models

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.todo.database.MyDatabase
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.ui.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "task"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SH_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideTaskDao(db: MyDatabase): TaskDao = db.taskDao()
}
