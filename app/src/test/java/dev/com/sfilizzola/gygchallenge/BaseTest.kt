package dev.com.sfilizzola.gygchallenge

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

abstract class BaseTest(enableServer:Boolean = true){
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val emptyURL = "/"

    lateinit var server: MockWebServer
    lateinit var retroFit: Retrofit

    init {

        if(enableServer) {
            setupRxPlugin()
            initMockServer()
        }
    }

    companion object {

        @AfterClass
        fun onTestExit() {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }

    fun setupRxPlugin() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    fun initMockServer() {
        server = MockWebServer()
        retroFit = retrofitBuilder(server.url(emptyURL).toString())
    }

    private fun retrofitBuilder(endPoint: String): Retrofit {
        val builder = OkHttpClient.Builder()
        val gson = GsonBuilder().create()

        return Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
    }
}