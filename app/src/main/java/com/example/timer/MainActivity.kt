package com.example.timer

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.timer.Utils.Companion.logd
import com.example.timer.Utils.Companion.toast


class MainActivity : Activity() {

	companion object {
		// "Pull Up" time for rest
//		const val COUNTDOWN_PULL_UP: Long = (2 * 60 + 50) * 1000 // 02:50
		const val COUNTDOWN_PULL_UP: Long = 5L // 00:05

		// "Press" time for rest
//        const val COUNTDOWN_PRESS: Long = (0 * 60 + 50) * 1000 // 00:50

		// "Bars" time for rest
//        const val COUNTDOWN_BARS: Long = (1 * 60 + 50) * 1000 // 01:50

		lateinit var context: Context
		lateinit var mediaPlayer: MediaPlayer
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		context = this
		mediaPlayer = MediaPlayer.create(this, R.raw.timer_finished)
		mediaPlayer.setOnErrorListener { mp, what, extra ->
			// file:///F:/GAMES/Android/SDK/docs/reference/android/media/MediaPlayer.OnErrorListener.html
			val strError = "MediaPlayer error: what = $what, extra = $extra"
			logd(strError)
			toast(strError)
			// True if the method handled the error, false if it didn't.
			false
		}

		WorkoutTimer(
				COUNTDOWN_PULL_UP,
				findViewById(R.id.textPullUp1),
				findViewById(R.id.buttonPullUpStart1),
				findViewById(R.id.buttonPullUpStop1)
		)
		WorkoutTimer(
				COUNTDOWN_PULL_UP,
				findViewById(R.id.textPullUp2),
				findViewById(R.id.buttonPullUpStart2),
				findViewById(R.id.buttonPullUpStop2)
		)
	}

	override fun onStop() {
		super.onStop()
		mediaPlayer.reset()
		mediaPlayer.release()
	}
}
