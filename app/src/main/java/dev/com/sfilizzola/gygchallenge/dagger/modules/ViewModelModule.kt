package dev.com.sfilizzola.gygchallenge.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.com.sfilizzola.gygchallenge.dagger.AppViewModelFactory
import dev.com.sfilizzola.gygchallenge.dagger.ViewModelKey
import dev.com.sfilizzola.gygchallenge.viewmodels.FavoritesFragmentViewModel
import dev.com.sfilizzola.gygchallenge.viewmodels.ListFragmentViewModel
import dev.com.sfilizzola.gygchallenge.viewmodels.MainActivityViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListFragmentViewModel::class)
    internal abstract fun bindListFragmentViewModel(listFragmentViewModel: ListFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesFragmentViewModel::class)
    internal abstract fun bindFavoritesFragmentViewModel(favoritesFragmentViewModel: FavoritesFragmentViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel


    @Binds
    internal abstract fun bindAppViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}