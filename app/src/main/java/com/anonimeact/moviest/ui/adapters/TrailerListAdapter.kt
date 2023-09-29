package com.anonimeact.moviest.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anonimeact.moviest.databinding.LayoutItemYoutubeListBinding
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.ui.YoutubePlayerActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class TrailerListAdapter(private val listMovie: ArrayList<TrailersItemModel>) :
    RecyclerView.Adapter<TrailerListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutItemYoutubeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutItemYoutubeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listMovie[position]
        with(holder.binding) {
            youtubeTrailer.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = data.key
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
            btnTapVideo.setOnClickListener {
                val intent = Intent(holder.itemView.context, YoutubePlayerActivity()::class.java)
                intent.putExtra("youtubeId", data.key)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listMovie.size

}
