package com.anonimeact.moviest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.FragmentDetailBinding
import com.anonimeact.moviest.databinding.LayoutItemGenreBinding
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.ui.adapters.ReviewListAdapter
import com.anonimeact.moviest.ui.adapters.TrailerListAdapter
import com.anonimeact.moviest.utils.toast
import com.anonimeact.moviest.utils.visible
import com.anonimeact.moviest.viewmodels.MovieFavoriteViewModel
import com.anonimeact.moviest.viewmodels.MovieViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val viewModelMovie: MovieViewModel by viewModels()
    private val viewModelFavorite: MovieFavoriteViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var reviewAdapter: ReviewListAdapter
    private lateinit var trailerAdapter: TrailerListAdapter
    private var listReview: ArrayList<ReviewItemModel> = ArrayList()
    private var listTrailer: ArrayList<TrailersItemModel> = ArrayList()
    private var movieId = 0
    private var detailMovieData : DetailMovieModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        movieId = arguments?.getInt("movieId") ?: 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Favorite Movie */
        binding.fabFavorite.setOnClickListener {
            if (binding.fabFavorite.tag == R.drawable.iv_favorite_outlane) {
                viewModelFavorite.addFavoriteMovie(detailMovieData, onFinished = {
                    requireContext().toast("Successfully added to favorites")
                    setupFavButtonIcon(detailMovieData)
                })
            } else {
                viewModelFavorite.deleteFavoriteMovie(detailMovieData?.id)
                requireContext().toast("Successfully deleted from favourites")
                setupFavButtonIcon(null)
            }
        }
        viewModelFavorite.favoriteMovieItem.observe(viewLifecycleOwner) { itemMovie ->
            setupFavButtonIcon(itemMovie)
        }

        with(viewModelMovie) {
            /** Detail Movie */
            viewModelMovie.detailMovie.observe(viewLifecycleOwner) {
                detailMovieData = it.data
                if (detailMovieData != null) {
                    binding.fabFavorite.visible()
                    setupDataDetailMovie()
                }
            }

            /** Movie Reviews */
            reviewAdapter = ReviewListAdapter(listReview)
            binding.rvReviews.apply {
                setHasFixedSize(true)
                adapter = reviewAdapter
            }
            reviews.observe(viewLifecycleOwner) {
                val reviews = it.data
                if (reviews != null) {
                    if (reviews.isEmpty()) {
                        binding.tvReviewEmpty.visible()
                        return@observe
                    }
                    listReview.addAll(reviews)
                    reviewAdapter.notifyDataSetChanged()
                }
            }

            /** Trailers */
            trailerAdapter = TrailerListAdapter(listTrailer)
            binding.rvTrailers.apply {
                adapter = trailerAdapter
                setHasFixedSize(true)
            }
            trailers.observe(viewLifecycleOwner) {
                val trailers = it.data
                if (trailers != null) {
                    listTrailer.addAll(trailers)
                    trailerAdapter.notifyDataSetChanged()
                    binding.llTrailerList.isVisible = trailers.isNotEmpty()
                }
            }
        }

        initRequestData()
    }

    private fun setupFavButtonIcon(itemMovie: DetailMovieModel?) {
        val icon =
            if (itemMovie == null) R.drawable.iv_favorite_outlane else R.drawable.iv_favorite_solid
        binding.fabFavorite.setImageResource(icon)
        binding.fabFavorite.tag = icon
    }

    private fun initRequestData() {
        viewModelMovie.getMovieDetail(movieId)
        viewModelMovie.getMovieReviews(movieId)
        viewModelMovie.getMovieTrailers(movieId)
        viewModelFavorite.getMovieFavoriteItem(movieId)
    }

    private fun setupDataDetailMovie() {
        with(binding) {
            tvMovieVoteAverage.text = detailMovieData?.vote_average.toString()
            tvSynopsis.text = detailMovieData?.overview
            val bannerPath = "https://image.tmdb.org/t/p/w780${detailMovieData?.backdrop_path}"
            Glide.with(requireContext()).load(bannerPath)
                .placeholder(R.drawable.placeholder_banner_movies).into(ivBannerMovie)
            setupGenreListView(detailMovieData)
        }
    }

    private fun FragmentDetailBinding.setupGenreListView(detailData: DetailMovieModel?) {
        detailData?.genres?.forEach { itemGenre ->
            val itemGenreView = LayoutItemGenreBinding.inflate(layoutInflater)
            itemGenreView.tvGenreName.text = itemGenre.name
            llListGenre.addView(itemGenreView.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}