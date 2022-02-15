package com.messieurme.mylinks.di

import android.content.Context
import com.messieurme.mylinks.BaseApplication
import com.messieurme.mylinks.di.modules.MainActivityModule
import com.messieurme.mylinks.di.modules.MemoryRoomModule
import com.messieurme.mylinks.di.modules.MemorySharedPrefModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        MemorySharedPrefModule::class,
        MemoryRoomModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}