package com.example.exoplayeryoutubesample

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayeryoutubesample.domain.youtube.Id
import com.example.exoplayeryoutubesample.fragment.BasicPlayerFragment
import com.example.exoplayeryoutubesample.infrastructure.http.HttpClient
import com.example.exoplayeryoutubesample.infrastructure.http.YoutubeHttp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        submit_button.setOnClickListener {
            val videoId = Id(searchinput.text.toString())
            validateAndAddFragment(videoId)
        }
    }

    private fun addVideoFragment(videoId: Id) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.rootLinerLayout, BasicPlayerFragment(this, videoId, YoutubeHttp(HttpClient)))
        fragmentTransaction.commit()
    }

    private fun validateAndAddFragment(id: Id) = runBlocking {
        withContext(Dispatchers.Default) { YoutubeHttp(HttpClient).fetchVideoInfo(id) }.let {
            Log.d(ContentValues.TAG, "validateAndAddFragment: $it")
            if (!it.isValid()) {
                Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
                return@let
            }
            addVideoFragment(id)
        }
    }
}