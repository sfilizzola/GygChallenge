package dev.com.sfilizzola.gygchallenge.dagger.modules

import dagger.Module
import dagger.Provides
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.network.NetworkClient
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideDataRepo(service: NetworkClient, reviewDao: ReviewDao):DataRepository{
        return DataRepository(service, reviewDao)
    }
}