package dev.com.sfilizzola.gygchallenge.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import dev.com.sfilizzola.gygchallenge.BaseApp
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.databinding.ActivityMainBinding
import dev.com.sfilizzola.gygchallenge.view.fragments.FavoritesFragment
import dev.com.sfilizzola.gygchallenge.view.fragments.ListFragment
import dev.com.sfilizzola.gygchallenge.view.viewStatus.MainViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.MainActivityViewModel
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject


class MainActivity : BaseAcitvity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        setUpNavigationBar()

        viewModel.getData().observe(this, Observer {
            it?.let { result ->
                when (result) {
                    is MainViewStatus.Success -> {
                        Timber.d("FavsList %s", it.toString())
                        BaseApp.favoriteList = it.favoriteList() as ArrayList<Int>
                        startListFragment()
                    }
                    is MainViewStatus.Error -> Timber.e(it.message())
                }
            }
        })

        viewModel.getFavorites()
    }

    private fun setUpNavigationBar() {

        binding.mainNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list_view -> {
                    startListFragment()
                    true
                }
                R.id.heart_view -> {
                    startFavoriteFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun startListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = ListFragment()
        transaction.replace(R.id.main_content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startFavoriteFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FavoritesFragment()
        transaction.replace(R.id.main_content,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}