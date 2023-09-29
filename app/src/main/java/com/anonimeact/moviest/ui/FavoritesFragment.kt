package com.anonimeact.moviest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.FragmentFavoritesBinding
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.ui.adapters.FavoriteMovieListAdapter
import com.anonimeact.moviest.viewmodels.MovieFavoriteViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterFavorite: FavoriteMovieListAdapter
    private var listFavorite: ArrayList<DetailMovieModel> = ArrayList()
    private val viewModelFavorite: MovieFavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterFavorite = FavoriteMovieListAdapter(listFavorite)
        adapterFavorite.listenerOnTapItem { detailMovie ->
            val bundle = Bundle()
            bundle.putInt("movieId", detailMovie.id)
            findNavController().navigate(
                R.id.action_FavoritesFragment_to_DetailMovieFragment, bundle
            )
            val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.title = detailMovie.title
        }
        with(binding) {
            rvFavorite.apply {
                adapter = adapterFavorite
                setHasFixedSize(true)
            }
        }

        viewModelFavorite.favoriteMovieList.observe(viewLifecycleOwner) { listMovie ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                listFavorite.clear()
                listMovie?.let { listFavorite.addAll(it) }
                binding.llNoData.isVisible = listFavorite.isEmpty()
                adapterFavorite.notifyDataSetChanged()
            }
        }
        viewModelFavorite.getMovieFavoriteList()
    }
}