package dev.com.sfilizzola.gygchallenge

import android.arch.lifecycle.Observer
import com.google.gson.Gson
import dev.com.sfilizzola.gygchallenge.database.DatabaseClient
import dev.com.sfilizzola.gygchallenge.database.daos.ReviewDao
import dev.com.sfilizzola.gygchallenge.network.NetworkClient
import dev.com.sfilizzola.gygchallenge.network.networkModels.BasicResponse
import dev.com.sfilizzola.gygchallenge.network.networkModels.BasicReview
import dev.com.sfilizzola.gygchallenge.repos.DataRepository
import dev.com.sfilizzola.gygchallenge.view.viewStatus.ListViewStatus
import dev.com.sfilizzola.gygchallenge.viewmodels.ListFragmentViewModel
import io.reactivex.Single
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import org.mockito.*


class ListFragmentViewModelTest : BaseTest() {

    private lateinit var viewModel: ListFragmentViewModel

    @Mock
    lateinit var dao: ReviewDao

    @Mock
    private lateinit var observer: Observer<ListViewStatus>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val network = retroFit.create(NetworkClient::class.java)
        val repository = DataRepository(network, dao)

        Mockito.`when`(dao.getAllReviews()).thenReturn(Single.just(ArrayList()))

        viewModel = ListFragmentViewModel(repository)
        viewModel.getData().observeForever(observer)

    }

    @Test
    fun shouldFetchSuccess() {
        val review = BasicReview(
                12345,
                "4.5",
                "Review Title",
                "This review is very good for testing",
                "Samuel Filizzola - Brazil",
                true,
                "September 8, 2018",
                "en",
                "solo",
                "Samuel Filizzola",
                "Brazil"
        )

        val list = ArrayList<BasicReview>()
        list.add(review)
        val response = BasicResponse(true, 1, list)

        mockServerResponse(200, response)

        viewModel.getReviews()

        Mockito.verify(observer).onChanged(ArgumentMatchers.any<ListViewStatus.Success>())
    }

    @Test
    fun shouldFetchFail() {
        mockServerResponse(500, BasicResponse())

        viewModel.getReviews()

        Mockito.verify(observer).onChanged(ArgumentMatchers.any<ListViewStatus.Error>())
    }

    private fun mockServerResponse(responseCode: Int, mockResponse: BasicResponse) {
        val mockResult = Gson().toJson(mockResponse)
        server.enqueue(MockResponse().setResponseCode(responseCode).setBody(mockResult))
    }


}