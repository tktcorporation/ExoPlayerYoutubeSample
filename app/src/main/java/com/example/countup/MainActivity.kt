package com.example.countup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var sheepCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var greeting = "こんにちは"
        val trialTime = Date()                         //追加
        val calendar = GregorianCalendar()             //追加
        calendar.time = trialTime                      //追加

        val hour = calendar.get(Calendar.HOUR_OF_DAY)  //追加
        if (hour in 1..11) {                           //追加
            greeting = "おはよう"                       //追加
        } else if (15 < hour) {                        //追加
            greeting = "こんばんは"                     //追加
        }                                              //追加

        textview.text = greeting

        rootLayout.setOnClickListener {             //追加
            sheepCount++                            //追加
            val sheepText = "ひつじが$sheepCount 匹"  //追加
            textview.text = sheepText               //追加
        }
    }
}