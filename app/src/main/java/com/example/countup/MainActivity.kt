package com.example.countup

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countup.domain.Greeting
import com.example.countup.domain.Sheeps
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val sheeps = Sheeps()
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview.text = Greeting(Date()).comment

        mp = MediaPlayer.create(applicationContext, R.raw.cry)

        rootLayout.setOnClickListener {
            sheeps.add()
            textview.text = sheeps.getCountText()

            when(sheeps.isStanding()) {
                true -> {
                    imageView1.setImageResource(R.drawable.sheep_sleeping)
                    mp?.start()
                }
                else -> imageView1.setImageResource(R.drawable.sheep_standing)
            }
        }
    }
}