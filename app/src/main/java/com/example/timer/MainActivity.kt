package com.example.timer

import android.app.Activity
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : Activity() {

    companion object {
        // These represent different important times
        // This is when the timer stops manually
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // "Pull Up" time for rest
//        const val COUNTDOWN_PULL_UP : Long = (2 * 60 + 50) * 1000 // 02:50
        const val COUNTDOWN_PULL_UP: Long = (0 * 60 + 3) * 1000 // 00:03

        // "Press" time for rest
        const val COUNTDOWN_PRESS: Long = (0 * 60 + 50) * 1000 // 00:50

        // "Bars" time for rest
        const val COUNTDOWN_BARS: Long = (1 * 60 + 50) * 1000 // 01:50
    }

    private val mediaPlayer = MediaPlayer.create(this, R.raw.sneeze)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textPullUp1 = findViewById<TextView>(R.id.timerPullUp1)
        textPullUp1.text = getTimeFromSeconds(TimeUnit.SECONDS.toMillis(COUNTDOWN_PULL_UP))

        val timerPullUp1 = object : CountDownTimer(COUNTDOWN_PULL_UP, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                textPullUp1.text = getTimeFromSeconds(secondsUntilFinished)
                Log.i("Timer", getTimeFromSeconds(secondsUntilFinished) as String)
            }

            override fun onFinish() {
                textPullUp1.setBackgroundColor(Color.RED)
                textPullUp1.setTextColor(Color.BLACK)
//                if (mediaPlayer.isPlaying) {
//                    mediaPlayer.stop()
//                }
                mediaPlayer.start()
            }
        }
        timerPullUp1.start()

    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.reset()
        mediaPlayer.release()
    }

    /**
     * Provides a String representation of the given time
     * @param seconds total amount of seconds
     * @return String? in "mm:ss" format
     */
    private fun getTimeFromSeconds(seconds: Long): String? {
        val timeZone = TimeZone.getTimeZone("UTC")
        val dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        dateFormatter.timeZone = timeZone
        return dateFormatter.format(Date(seconds * 1000))
    }
}