package com.example.exoplayeryoutubesample.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exoplayeryoutubesample.R
import com.example.exoplayeryoutubesample.domain.youtube.Id
import com.example.exoplayeryoutubesample.infrastructure.ExoPlayerHelper
import com.example.exoplayeryoutubesample.infrastructure.http.YoutubeHttp
import com.google.android.exoplayer2.*
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BasicPlayerFragment(private val ctx: Context, private val videoId: Id, private val youtubeHttp: YoutubeHttp): Fragment() {
    private lateinit var exoPlayerHelper: ExoPlayerHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exoPlayerHelper = ExoPlayerHelper(ctx)
        exoPlayerHelper.initializePlayer(eventListener)
        reloadPlayer()
    }

    private val eventListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
            Log.d(TAG, "onPlayerError()")

            when(error.type) {
                ExoPlaybackException.TYPE_SOURCE -> {
                    Log.d(TAG, "error - TYPE_SOURCE")
                    reloadPlayer()
                }
                ExoPlaybackException.TYPE_RENDERER -> {
                    Log.d(TAG, "error - TYPE_RENDERER")
                }
                ExoPlaybackException.TYPE_UNEXPECTED -> {
                    Log.d(TAG, "error - TYPE_UNEXPECTED")
                }
            }
        }
    }

    private fun reloadPlayer() = runBlocking {
        withContext(Dispatchers.Default) { youtubeHttp.fetchVideoInfo(videoId).extractM3U8URL() }.let {
            Log.d(TAG, "reloadPlayer: $it")
            if (it == null) {
                Log.e(TAG, "it can't play this video.")
                Toast.makeText(ctx, "it can't play this video.", Toast.LENGTH_SHORT).show()
                return@let
            }
            exoPlayerHelper.setPlayerMediaSource(it)
            exoPlayerHelper.prepare()
            player_view.player = exoPlayerHelper.getPlayer()
        }
    }
}