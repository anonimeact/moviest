package com.anonimeact.moviest.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.FragmentHomeBinding
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.ui.adapters.MovieListAdapter
import com.anonimeact.moviest.utils.gone
import com.anonimeact.moviest.utils.visible
import com.anonimeact.moviest.viewmodels.MovieViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModelMovie: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieListAdapter
    private var listMovies: ArrayList<MovieItemModel> = ArrayList()
    private var page = 1
    private var isLoadMovieFreeze = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fabShowFavorite.setOnClickListener { findNavController().navigate(R.id.action_HomeFragment_to_FavoritesFragment) }
            swipeToReload.setOnRefreshListener {
                page = 1
                listMovies.clear()
                viewModelMovie.getMovieList(page)
            }
            movieAdapter = MovieListAdapter(listMovies)
            movieAdapter.listenerOnTapItem {
                val bundle = Bundle()
                bundle.putInt("movieId", it.id)
                findNavController().navigate(R.id.action_Home_to_Detail, bundle)
                val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
                toolbar.title = it.title
            }
            rvMovieList.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, 1)
                adapter = movieAdapter
            }
            rvMovieList.viewTreeObserver.addOnScrollChangedListener {
                if (!isLoadMovieFreeze && page > 1) {
                    val lastItem = rvMovieList.getChildAt(rvMovieList.childCount - 1) as View
                    val diff: Int = lastItem.bottom - (rvMovieList.height + rvMovieList
                        .scrollY)

                    if (diff == 0) {
                        viewModelMovie.getMovieList(page)
                        animBottom.visible()
                        isLoadMovieFreeze = true
                    }
                }
            }
        }

        viewModelMovie.movies.observe(viewLifecycleOwner) { result ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                val dataMovie = result?.data
                if (dataMovie != null) {
                    listMovies.addAll(dataMovie)
                    isLoadMovieFreeze = dataMovie.isEmpty()
                    page++
                    if (binding.llErrorLoadData.isVisible)
                        binding.llErrorLoadData.gone()
                } else {
                    if (page == 1) {
                        listMovies.clear()
                        binding.llErrorLoadData.visible()
                    }
                }
                movieAdapter.notifyDataSetChanged()
                with(binding) {
                    if (swipeToReload.isRefreshing)
                        swipeToReload.isRefreshing = false
                }
            }
            hideShimmerAndBottomLoading()
        }

        if (listMovies.isEmpty()) {
            binding.shimmerLoadingMain.startShimmer()
            viewModelMovie.getMovieList(page)
        }

    }

    private fun hideShimmerAndBottomLoading() {
        with(binding) {
            with(shimmerLoadingMain) {
                if (isVisible) {
                    stopShimmer()
                    gone()
                }
            }
            if (animBottom.isVisible)
                Handler(Looper.getMainLooper()).postDelayed({
                    animBottom.gone()
                }, 1000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}