package com.example.countup.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.countup.R
import com.example.countup.infrastructure.http.YoutubeHttp
import kotlinx.android.synthetic.main.url_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UrlTextFragment(private val ctx: Context) : Fragment() {

    private var m3u8url: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.url_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        setUrlText()
        start_video_button.setOnClickListener {
            loadVideoFragment()
        }
    }

    private fun loadVideoFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        Log.d(TAG, "loadVideoFragment: $m3u8url")
        fragmentTransaction?.add(R.id.rootLayout, BasicPlayerFragment(ctx, Uri.parse(Uri.decode(m3u8url)), YoutubeHttp()))
        fragmentTransaction?.commit()
    }

    private fun setM3U8url(url: String) {
        m3u8url = url
        m3u8text.text = url
    }

    //非同期処理でHTTP GETを実行します。
    private fun setUrlText() = runBlocking {
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        withContext(Dispatchers.Default) { YoutubeHttp().fetchM3U8Url() }.let {
            Log.d(TAG, "setUrlText: $it")
            setM3U8url(it)
        }
    }
}