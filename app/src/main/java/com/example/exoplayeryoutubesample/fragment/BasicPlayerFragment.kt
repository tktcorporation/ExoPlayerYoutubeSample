package com.example.exoplayeryoutubesample.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exoplayeryoutubesample.R
import com.example.exoplayeryoutubesample.infrastructure.http.ExoPlayerHelper
import com.example.exoplayeryoutubesample.infrastructure.http.YoutubeHttp
import com.google.android.exoplayer2.*
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BasicPlayerFragment(private val ctx: Context, private val uri : Uri, private val youtubehttp: YoutubeHttp): Fragment() {
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

        val eventListener = object : Player.EventListener {
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

        exoPlayerHelper = ExoPlayerHelper(ctx)
        exoPlayerHelper.initializePlayer(uri, eventListener)
        player_view.player = exoPlayerHelper.getPlayer()
    }

    //非同期処理でHTTP GETを実行します。
    private fun reloadPlayer() = runBlocking {
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        withContext(Dispatchers.Default) { YoutubeHttp().fetchM3U8Url() }.let {
            Log.d(TAG, "reloadPlayer: $it")
            exoPlayerHelper.rebuildPlayerMediaSource(Uri.parse(it))
            player_view.player = exoPlayerHelper.getPlayer()
        }
    }
}