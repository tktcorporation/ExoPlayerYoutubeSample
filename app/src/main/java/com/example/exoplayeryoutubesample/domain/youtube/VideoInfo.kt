package com.example.exoplayeryoutubesample.domain.youtube

import android.net.Uri

const val m3u8Regex = "(https%3A%2F%2Fmanifest.googlevideo.com.+m3u8)"
const val failStatus = "(status=fail)"

class VideoInfo(private val body: String) {
    fun extractM3U8URL(): Uri? {
        val urls = m3u8Regex.toRegex(RegexOption.IGNORE_CASE).findAll(body).map{it.value}.toList()
        if (urls.count() < 1) {
            return null
        }
        return Uri.parse(Uri.decode(urls[0]))
    }
    fun isValid(): Boolean {
        val urls = failStatus.toRegex(RegexOption.IGNORE_CASE).findAll(body).map{it.value}
        if (urls.count() > 0) {
            return false
        }
        return true
    }
}