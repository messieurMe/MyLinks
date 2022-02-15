package com.messieurme.mylinks.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MemorySharedPrefModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("FirstLaunch", Context.MODE_PRIVATE)
}
