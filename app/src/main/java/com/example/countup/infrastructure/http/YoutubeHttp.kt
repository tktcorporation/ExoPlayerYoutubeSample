package com.example.countup.infrastructure.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

object HttpClient {
    val instance = OkHttpClient()
}

class YoutubeHttp() {
    private val youtubeVideoUrl = "https://example.com/login"

    // Function that makes the network request, blocking the current thread
    suspend fun fetchM3U8Url(): String {
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
        val regex = "(https%3A%2F%2Fmanifest.googlevideo.com.+m3u8)"
        val urls = regex.toRegex(RegexOption.IGNORE_CASE).findAll(body).map{it.value}
        val url = urls.toList()[0]
        return url
    }
}