package com.example.countup.domain

import java.util.*

class Greeting(date: Date) {
    val comment: String

    init {
        val calendar = GregorianCalendar()
        calendar.time = date

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        comment = when {
            hour in 1..11 -> {
                "おはよう"
            }
            15 < hour -> {
                "こんばんは"
            }
            else -> {
                "こんにちは"
            }
        }
    }
}