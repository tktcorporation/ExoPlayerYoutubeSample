package com.example.countup

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.countup.domain.Greeting
import com.example.countup.domain.Sheep
import com.example.countup.infrastructure.http.YoutubeHttp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private val sheep = Sheep()
    private var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview.text = Greeting(Date()).comment

        mp = MediaPlayer.create(applicationContext, R.raw.cry)

        rootLayout.setOnClickListener {
            sheep.add()
            textview.text = sheep.getCountText()
            onParallelGetButtonClick()

            when(sheep.isStanding()) {
                true -> {
                    imageView1.setImageResource(R.drawable.sheep_sleeping)
                    mp?.start()
                }
                else -> imageView1.setImageResource(R.drawable.sheep_standing)
            }
        }
    }

    //非同期処理でHTTP GETを実行します。
    private fun onParallelGetButtonClick() = runBlocking {
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        withContext(Dispatchers.Default) { YoutubeHttp().fetchM3U8Url() }.let {
            m3u8text.text = it
        }
    }
}