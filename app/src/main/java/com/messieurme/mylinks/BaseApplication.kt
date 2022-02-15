package com.messieurme.mylinks

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.messieurme.mylinks.di.ApplicationComponent
import com.messieurme.mylinks.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.factory().create(this.applicationContext)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    

    companion object {
        lateinit var instance : BaseApplication
            private set
    }
}