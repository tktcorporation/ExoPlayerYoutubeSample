package com.example.countup.infrastructure.http

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.lang.Error

class ExoPlayerHelper(
    private val ctx: Context,
) {

    private var exoPlayer: ExoPlayer? = null
    private var mediaSource: ProgressiveMediaSource? = null

    private val defaultEventListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
            Log.d(ContentValues.TAG, "onPlayerError()")
            when(error.type) {
                ExoPlaybackException.TYPE_SOURCE -> {
                    Log.d(ContentValues.TAG, "error - TYPE_SOURCE")
                }
                ExoPlaybackException.TYPE_RENDERER -> {
                    Log.d(ContentValues.TAG, "error - TYPE_RENDERER")
                }
                ExoPlaybackException.TYPE_UNEXPECTED -> {
                    Log.d(ContentValues.TAG, "error - TYPE_UNEXPECTED")
                }
            }
        }
    }

    fun initializePlayer(uri: Uri, eventListener: Player.EventListener = defaultEventListener) {
        exoPlayer = SimpleExoPlayer.Builder(ctx).setBandwidthMeter(DefaultBandwidthMeter.getSingletonInstance(ctx)).build()
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer?.playWhenReady = true
        exoPlayer?.addListener(eventListener)
        exoPlayer?.setMediaSource(buildMediaSource(uri))
        exoPlayer?.prepare()
    }

    fun rebuildPlayerMediaSource(uri: Uri)  {
        Log.d(ContentValues.TAG, "rebuildPlayerMediaSource")
        exoPlayer?.setMediaSource(buildMediaSource(uri))
        exoPlayer?.prepare()
    }

    fun getPlayer(): ExoPlayer {
        if (exoPlayer == null) {throw Error("player is not found")}
        return exoPlayer!!
    }

    private fun killPlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.release()
            exoPlayer = null
            mediaSource = null
        }
    }

    private fun createDataSource(): DefaultDataSourceFactory {
        return DefaultDataSourceFactory(
            ctx,
            Util.getUserAgent(ctx, "ExoPlayer")
        )
    }

    private fun buildMediaSource(uri: Uri): HlsMediaSource {
        return HlsMediaSource
            .Factory(createDataSource())
            .createMediaSource(MediaItem.fromUri(uri))
    }


}