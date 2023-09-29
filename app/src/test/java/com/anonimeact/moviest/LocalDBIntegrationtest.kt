package com.anonimeact.moviest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.Genre
import com.anonimeact.moviest.repositories.MovieFavoriteRepositories
import com.anonimeact.moviest.viewmodels.MovieFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LocalDBIntegrationtest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: MovieFavoriteRepositories

    @Mock
    private lateinit var favoriteMovieListObserver: Observer<List<DetailMovieModel>?>

    @InjectMocks
    private lateinit var viewModel: MovieFavoriteViewModel

    private val mockGenre = Genre(1, "Action")
    private val mockDetailMovie = DetailMovieModel(
        favorite_id = 1,
        backdrop_path = "/backdrop_path.jpg",
        genres = listOf(mockGenre),
        id = 123,
        imdb_id = "tt1234567",
        original_title = "Original Title",
        overview = "Overview of the movie",
        popularity = 123.45,
        poster_path = "/poster_path.jpg",
        status = "Released",
        tagline = "Tagline of the movie",
        title = "Movie Title",
        vote_average = 7.8
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun testGetMovieFavoriteList() = runBlocking {
        val dummyFavoriteMovieList: List<DetailMovieModel> = listOf(mockDetailMovie)
        `when`(repo.getFavoriteMovieList()).thenReturn(dummyFavoriteMovieList)

        viewModel.favoriteMovieList.observeForever(favoriteMovieListObserver)
        viewModel.getMovieFavoriteList()

        verify(repo, times(1)).getFavoriteMovieList()
        verify(favoriteMovieListObserver, times(1)).onChanged(dummyFavoriteMovieList)

        assertEquals(dummyFavoriteMovieList, viewModel.favoriteMovieList.value)
    }
}