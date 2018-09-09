package dev.com.sfilizzola.gygchallenge.view.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.databinding.ActivityMainBinding
import dev.com.sfilizzola.gygchallenge.view.fragments.ListFragment


class MainActivity : BaseAcitvity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpNavigationBar()
        startListFragment()
    }

    private fun setUpNavigationBar() {

        binding.mainNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.list_view -> {
                    startListFragment()
                    true
                }
                R.id.heart_view -> {
                    startFavoriteFragment()
                    true
                } else -> false
            }
        }
    }

    private fun startListFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = ListFragment()
        transaction.replace(R.id.main_content,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startFavoriteFragment(){
//        val transaction = supportFragmentManager.beginTransaction()
//        val fragment = MapFragment()
//        transaction.replace(R.id.main_content,fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
    }
}