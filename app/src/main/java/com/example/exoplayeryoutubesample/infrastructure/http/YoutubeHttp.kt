package com.example.exoplayeryoutubesample.infrastructure.http

import android.net.Uri
import android.util.Log
import com.example.exoplayeryoutubesample.domain.YoutubeInfo
import okhttp3.OkHttpClient
import okhttp3.Request

object HttpClient {
    val instance = OkHttpClient()
}

class YoutubeHttp() {
    // Function that makes the network request, blocking the current thread
    fun fetchM3U8Url(): String {
        val client = HttpClient.instance
        val request = Request.Builder()
            .url("https://www.youtube.com/get_video_info?video_id=rvkxtVkvawc")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Safari/605.1.15")
            .build()

        val response = client.newCall(request).execute()
        var body = response.body?.string()
        Log.d("TAG", body.toString())
        if (body == null) {
            Log.e("body == null", "onViewCreated: received body is null.")
            body = "sss"
        }
        return Uri.decode(YoutubeInfo(body).extractM3U8URL())
    }
}