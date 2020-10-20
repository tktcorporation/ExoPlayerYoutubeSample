package com.example.countup.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.countup.R
import com.example.countup.infrastructure.http.ExoPlayerHelper
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.IOException


class PlayerFragment(private val ctx: Context, private val url: String) : Fragment() {

    private var playerView: PlayerView? = null

    private val bandwidthMeter: DefaultBandwidthMeter by lazy {
        DefaultBandwidthMeter.Builder(ctx).build()
    }

    private val exoPlayer: ExoPlayer? by lazy {
        ExoPlayer.Builder(ctx).setBandwidthMeter(bandwidthMeter).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: $url")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val helper = ExoPlayerHelper(ctx)
        helper.initializePlayer(url)
    }

    private fun createPlayer() {
        val handler = Handler()

        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
//        exoPlayer?.addListener(eventListener)

        playerView?.player = exoPlayer

        val userAgent =
            Util.getUserAgent(ctx, "exoPlayer")
        val mediaSource = ProgressiveMediaSource
            .Factory(
                DefaultDataSourceFactory(ctx, userAgent),
                DefaultExtractorsFactory()
            )
            .createMediaSource(MediaItem.fromUri(url))

        exoPlayer?.setMediaSource(mediaSource, true)
        exoPlayer!!.playWhenReady = true

        //SurfaceViewに反映
        //(exoPlayer as SimpleExoPlayer).setVideoSurfaceView(playerView)
        playerView?.player = exoPlayer
        //再生
        exoPlayer?.playWhenReady = true
        //リピートon
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}