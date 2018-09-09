package dev.com.sfilizzola.gygchallenge.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.com.sfilizzola.gygchallenge.dagger.AppViewModelFactory
import dev.com.sfilizzola.gygchallenge.dagger.ViewModelKey
import dev.com.sfilizzola.gygchallenge.viewmodels.ListFragmentViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListFragmentViewModel::class)
    internal abstract fun bindListFragmentViewModel(listFragmentViewModel: ListFragmentViewModel): ViewModel

    @Binds
    internal abstract fun bindAppViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}