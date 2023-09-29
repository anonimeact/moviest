package com.anonimeact.moviest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.LayoutItemMovieBinding
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.utils.formatShortDate
import com.bumptech.glide.Glide

class MovieListAdapter(private val listMovie: ArrayList<MovieItemModel>) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listMovie[position]
        val context = holder.itemView.context
        with(holder.binding) {
            tvMovieName.text = data.title
            tvMovieReleaseDate.text = context.getString(R.string.release_on, data.release_date.formatShortDate())
            val posterUrl = "https://image.tmdb.org/t/p/w342${data.poster_path}"
            Glide.with(context).load(posterUrl)
                .placeholder(R.drawable.placeholder_moviest).into(ivMoviePoster)

            root.setOnClickListener {
                onTapItem(data)
            }
        }
    }

    override fun getItemCount(): Int = listMovie.size

    private var onTapItem: (MovieItemModel) -> Unit = {}
    fun listenerOnTapItem(data: (item: MovieItemModel) -> Unit) {
        onTapItem = data
    }
}
