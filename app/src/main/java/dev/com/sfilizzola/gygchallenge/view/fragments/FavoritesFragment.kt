package dev.com.sfilizzola.gygchallenge.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.com.sfilizzola.gygchallenge.R
import dev.com.sfilizzola.gygchallenge.adapter.ReviewListAdapter
import dev.com.sfilizzola.gygchallenge.databinding.FragmentFavoritesBinding
import dev.com.sfilizzola.gygchallenge.databinding.FragmentListBinding
import dev.com.sfilizzola.gygchallenge.view.viewStatus.FavoriteViewStatus

import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.FavoritesFragmentViewModel
import dev.com.sfilizzola.gygchallenge.viewmodels.ListFragmentViewModel
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(FavoritesFragmentViewModel::class.java) }

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var reviewAdapter: ReviewListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.viewModel = viewModel

        reviewAdapter = ReviewListAdapter(viewModel.getData())

        with(binding.favoritesRecycler) {
            this.setHasFixedSize(true)
            this.adapter = reviewAdapter
            this.layoutManager = LinearLayoutManager(context)
        }

        viewModel.getData().observe(this, Observer {
            it?.let { result ->
                when (result) {
                    is FavoriteViewStatus.Success -> {
                        if (it.list().isEmpty()) {
                            viewModel.showEmptyMessage()
                        } else {
                            reviewAdapter.update(it.list())
                        }
                    }
                    is FavoriteViewStatus.Error -> displaySnackBarError()
                    is FavoriteViewStatus.Click -> {
                        it.review()?.let {
                            viewModel.deleteReview(viewModel.reviewToFavoriteMapper(it))
                            reviewAdapter.removeItem(it)
                        }
                    }
                }
            }
        })

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviews()
    }


    private fun displaySnackBarError() {
        Snackbar.make(binding.root, R.string.retry_unknow_issue, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_text) {
                    viewModel.getReviews()
                }.show()
    }
}