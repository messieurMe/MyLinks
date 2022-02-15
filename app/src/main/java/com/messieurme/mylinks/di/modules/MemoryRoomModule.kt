package com.messieurme.mylinks.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.messieurme.mylinks.repository.room.AppDatabase
import com.messieurme.mylinks.repository.room.LinksFamilyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MemoryRoomModule {
    @Singleton
    @Provides
    fun provideLinkFamilyDao(context: Context): LinksFamilyDao =
        provideRoomDatabase(context).linkFamilyDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()

}