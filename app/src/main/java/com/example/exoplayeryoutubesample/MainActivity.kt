package com.example.exoplayeryoutubesample

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayeryoutubesample.domain.Greeting
import com.example.exoplayeryoutubesample.domain.Sheep
import com.example.exoplayeryoutubesample.fragment.UrlTextFragment
import kotlinx.android.synthetic.main.activity_main.*

import java.util.*

class MainActivity() : AppCompatActivity() {

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
            addFirstFragment()

            when(sheep.isStanding()) {
                true -> {
                    imageView1.setImageResource(R.drawable.sheep_sleeping)
                    mp?.start()
                }
                else -> imageView1.setImageResource(R.drawable.sheep_standing)
            }
        }
    }



    private fun addFirstFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.rootLayout, UrlTextFragment(this))
        fragmentTransaction.commit()
    }
}