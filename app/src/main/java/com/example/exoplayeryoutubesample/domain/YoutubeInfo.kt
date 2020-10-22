package com.example.exoplayeryoutubesample.domain

class YoutubeInfo(private val info: String) {
    private val m3u8Regex = "(https%3A%2F%2Fmanifest.googlevideo.com.+m3u8)"

    fun extractM3U8URL(): String {
        val urls = m3u8Regex.toRegex(RegexOption.IGNORE_CASE).findAll(info).map{it.value}
        return urls.toList()[0]
    }
}