package com.example.countup.infrastructure.http

import android.content.Context
import android.net.Uri
import com.example.countup.R
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoPlayerHelper(
    private val ctx: Context,
//    onError: (ExoPlaybackException) -> Unit,
//    onPlayerBuffer: (Boolean) -> Unit
) {

    var exoPlayer: ExoPlayer? = null
    private var mediaSource: ProgressiveMediaSource? = null

    private val playerListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
//            onPlayerError(error)
//            onError(error)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
//            onPlaybackStateChanged(playbackState)
//            onPlayerBuffer(playbackState == Player.STATE_BUFFERING)
        }
    }

    fun initializePlayer(url: String) {
        exoPlayer = SimpleExoPlayer.Builder(ctx).build()
        exoPlayer!!.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer!!.addListener(playerListener)

        val userAgent =
            Util.getUserAgent(ctx, ctx.getString(R.string.app_name))
        mediaSource = ProgressiveMediaSource
            .Factory(
                DefaultDataSourceFactory(ctx, userAgent),
                DefaultExtractorsFactory()
            )
            .createMediaSource(Uri.parse(url))

        exoPlayer!!.prepare(mediaSource!!, true, false)
        exoPlayer!!.playWhenReady = true
    }

    private fun killPlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.release()
            exoPlayer = null
            mediaSource = null
        }
    }
}