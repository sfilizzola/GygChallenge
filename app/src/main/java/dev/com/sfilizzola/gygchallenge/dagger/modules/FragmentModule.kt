package dev.com.sfilizzola.gygchallenge.dagger.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.com.sfilizzola.gygchallenge.view.fragments.ListFragment

@Module
abstract class FragmentModule{

    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment

}