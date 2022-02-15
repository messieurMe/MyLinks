package com.messieurme.mylinks.di.modules

import androidx.lifecycle.ViewModel
import com.messieurme.mylinks.MainActivity
import com.messieurme.mylinks.di.factories.ViewModelBuilder
import com.messieurme.mylinks.di.factories.ViewModelKey
import com.messieurme.mylinks.ui.screens.mainScreen.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun MainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: MainViewModel): ViewModel

}