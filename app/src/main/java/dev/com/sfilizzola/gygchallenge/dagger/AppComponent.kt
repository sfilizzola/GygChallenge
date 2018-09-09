package dev.com.sfilizzola.gygchallenge.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.com.sfilizzola.gygchallenge.dagger.modules.NetworkModule
import dev.com.sfilizzola.gygchallenge.dagger.modules.RepoModule
import dev.com.sfilizzola.gygchallenge.dagger.modules.ViewModelModule
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.dagger.modules.ActivityModule
import dev.com.sfilizzola.gygchallenge.dagger.modules.DatabaseModule
import dev.com.sfilizzola.gygchallenge.dagger.modules.FragmentModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (NetworkModule::class),
    (ActivityModule::class),
    (FragmentModule::class),
    (DatabaseModule::class),
    (RepoModule::class),
    (ViewModelModule::class)])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: BaseApp): Builder

        fun build(): AppComponent
    }

    fun inject(application: BaseApp)
}