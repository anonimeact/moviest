package com.anonimeact.moviest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anonimeact.moviest.R
import com.anonimeact.moviest.databinding.LayoutItemReviewUserBinding
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.utils.formatLongDate
import com.bumptech.glide.Glide

class ReviewListAdapter(private val listMovie: ArrayList<ReviewItemModel>) :
    RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemReviewUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemReviewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listMovie[position]
        with(holder.binding) {
            tvReviewAuthorName.text = data.author
            tvReviewContent.text = "\"${data.content}\""
            tvReviewUpdateAt.text = data.updated_at.formatLongDate()
            val posterUrl = "https://image.tmdb.org/t/p/w342${data.author_details.avatar_path}"
            Glide.with(holder.itemView.context).load(posterUrl)
                .placeholder(R.drawable.ic_user_placeholder).into(ivUserAvater)
        }
    }

    override fun getItemCount(): Int = listMovie.size

}
