package com.anonimeact.moviest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anonimeact.moviest.models.ApiHandlerModel
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.repositories.MovieRepositories
import com.anonimeact.moviest.viewmodels.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieAPIIntegrationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: MovieRepositories

    @Mock
    private lateinit var moviesObserver: Observer<ApiHandlerModel<List<MovieItemModel>?, String?>>

    @Mock
    private lateinit var detailMovieObserver: Observer<ApiHandlerModel<DetailMovieModel?, String?>>

    @Mock
    private lateinit var reviewsObserver: Observer<ApiHandlerModel<List<ReviewItemModel>?, String?>>

    @Mock
    private lateinit var trailersObserver: Observer<ApiHandlerModel<List<TrailersItemModel>?, String?>>

    @InjectMocks
    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun testGetMovieListEmpty() = runBlocking {
        val dummyMovieList: ApiHandlerModel<List<MovieItemModel>?, String?> = ApiHandlerModel(null, "Error Networking")
        `when`(repo.getMovieList(anyInt())).thenReturn(dummyMovieList)

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMovieList(anyInt())

        verify(repo, times(1)).getMovieList(anyInt())
        verify(moviesObserver, times(1)).onChanged(dummyMovieList)

        assertEquals(null, viewModel.movies.value?.data)
    }

    @Test
    fun testGetMovieList() = runBlocking {
        val dummyMovieList = ApiHandlerModel(
            listOf(
                MovieItemModel(
                    id = 1,
                    original_title = "Movie 1",
                    overview = "Overview of Movie 1",
                    popularity = 7.8,
                    poster_path = "path/to/poster1.jpg",
                    title = "Movie 1",
                    vote_average = 8.0,
                    vote_count = 100,
                    release_date = "2023-01-01"
                ),
                MovieItemModel(
                    id = 2,
                    original_title = "Movie 2",
                    overview = "Overview of Movie 2",
                    popularity = 8.2,
                    poster_path = "path/to/poster2.jpg",
                    title = "Movie 2",
                    vote_average = 8.5,
                    vote_count = 150,
                    release_date = "2023-02-15"
                )
            ),
            null
        )

        `when`(repo.getMovieList(1)).thenReturn(dummyMovieList)

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMovieList(1)

        verify(repo, times(1)).getMovieList(1)
        verify(moviesObserver, times(1)).onChanged(dummyMovieList)

        assertEquals(dummyMovieList.data, viewModel.movies.value?.data)
    }

    @Test
    fun testGetMovieDetailEmpty() = runBlocking {
        val dummyMovieDetail: ApiHandlerModel<DetailMovieModel?, String?> = ApiHandlerModel(null, "Error Networking")
        `when`(repo.getMovieDetail(anyInt())).thenReturn(dummyMovieDetail)

        viewModel.detailMovie.observeForever(detailMovieObserver)
        viewModel.getMovieDetail(1)

        verify(repo, times(1)).getMovieDetail(1)
        verify(detailMovieObserver, times(1)).onChanged(dummyMovieDetail)
        assertEquals(null, viewModel.movies.value?.data)
    }

    @Test
    fun testGetMovieReviewsEmpty() = runBlocking {
        val dummyMovieReviews: ApiHandlerModel<List<ReviewItemModel>?, String?> = ApiHandlerModel(emptyList(), "Error Networking")
        `when`(repo.getMovieReviews(anyInt())).thenReturn(dummyMovieReviews)

        viewModel.reviews.observeForever(reviewsObserver)
        viewModel.getMovieReviews(1)

        verify(repo, times(1)).getMovieReviews(1)
        verify(reviewsObserver, times(1)).onChanged(dummyMovieReviews)
        assertEquals(null, viewModel.movies.value?.data)
    }

    @Test
    fun testGetMovieTrailersEmpty() = runBlocking {
        val dummyMovieTrailers: ApiHandlerModel<List<TrailersItemModel>?, String?> = ApiHandlerModel(emptyList(), "Error Networking")
        `when`(repo.getMovieTrailers(anyInt())).thenReturn(dummyMovieTrailers)

        viewModel.trailers.observeForever(trailersObserver)
        viewModel.getMovieTrailers(1)

        verify(repo, times(1)).getMovieTrailers(1)
        verify(trailersObserver, times(1)).onChanged(dummyMovieTrailers)
    }
}