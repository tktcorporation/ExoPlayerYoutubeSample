package com.example.countup.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.countup.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import kotlinx.android.synthetic.main.fragment_player.*

class BasicPlayerFragment(private val ctx: Context, private val uri : Uri): Fragment() {
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exoPlayer = SimpleExoPlayer.Builder(ctx).build()
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer.playWhenReady = true
        exoPlayer.setMediaSource(buildMediaSource())
        exoPlayer.prepare()
        player_view.player = exoPlayer
    }

    private fun createDataSource(): DefaultDataSourceFactory {
        return DefaultDataSourceFactory(
            ctx,
            getUserAgent(ctx, "ExoPlayer")
        )
    }

    private fun buildMediaSource(): HlsMediaSource {
        return HlsMediaSource
            .Factory(createDataSource())
            .createMediaSource(MediaItem.fromUri(uri))
    }
}