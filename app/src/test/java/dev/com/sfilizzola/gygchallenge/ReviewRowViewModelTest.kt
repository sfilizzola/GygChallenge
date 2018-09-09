package dev.com.sfilizzola.gygchallenge

import android.arch.lifecycle.MutableLiveData
import dev.com.sfilizzola.gygchallenge.models.Review
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.ReviewRowViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ReviewRowViewModelTest {

    private lateinit var viewModel: ReviewRowViewModel

    @Before
    fun setUp() {

        val review = Review(12345,
                "4.5",
                "Review Title",
                "This review is very good for testing",
                "Samuel Filizzola - Brazil",
                true,
                "September 8, 2018",
                "en",
                "solo",
                "Samuel Filizzola",
                "Brazil",
                true)

        val data = MutableLiveData<ListViewStatus>()

        viewModel = ReviewRowViewModel(review, data)

    }

    @Test
    fun shouldShowReviewData(){
        assertEquals("Invalid title!", "Review Title", viewModel.getTitle())
        assertEquals("Invalid message!", "This review is very good for testing", viewModel.getMessage())
        assertEquals("Invalid reviwer Name!", "Samuel Filizzola", viewModel.getAuthorName())
        assertEquals("Invalid country!", "Brazil", viewModel.getAuthorCountry())
        assertEquals("Invalid date!", "September 8, 2018", viewModel.getDate())
        assertEquals("Invalid rating!", "4.5", viewModel.getRating())
        assertEquals("Invalid favorite!", true, viewModel.getFavorite())
    }
}