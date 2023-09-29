package com.anonimeact.moviest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anonimeact.moviest.databinding.ActivityYoutubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YoutubePlayerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityYoutubePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val youtubeId = intent.getStringExtra("youtubeId") ?: ""
        binding = ActivityYoutubePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
           youtubePlayerViewTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(youtubeId, 0f)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerViewTrailer.release()
    }
}