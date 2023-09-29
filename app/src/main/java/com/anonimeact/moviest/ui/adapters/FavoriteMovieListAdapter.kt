package com.anonimeact.moviest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.LayoutItemFavoriteMovieBinding
import com.anonimeact.moviest.models.DetailMovieModel
import com.bumptech.glide.Glide

class FavoriteMovieListAdapter(private val listMovie: ArrayList<DetailMovieModel>) :
    RecyclerView.Adapter<FavoriteMovieListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemFavoriteMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listMovie[position]
        with(holder.binding) {
            tvMovieTitle.text = data.title
            tvMovieOverview.text = data.overview
            val bannerPath = "https://image.tmdb.org/t/p/w780${data.backdrop_path}"
            Glide.with(holder.itemView.context).load(bannerPath)
                .placeholder(R.drawable.placeholder_banner_movies).into(ivMovieBanner)
            root.setOnClickListener { onTapItem(data) }
        }
    }

    override fun getItemCount(): Int = listMovie.size

    private var onTapItem: (DetailMovieModel) -> Unit = {}
    fun listenerOnTapItem(data: (item: DetailMovieModel) -> Unit) {
        onTapItem = data
    }
}
