package com.example.timer

import android.app.Activity
import android.os.Bundle


class MainActivity : Activity() {

	companion object {
		// "Pull Up" time for rest
//		const val COUNTDOWN_PULL_UP: Long = (2 * 60 + 50) * 1000 // 02:50
		const val COUNTDOWN_PULL_UP: Long = 5L // 00:05

		// "Press" time for rest
//        const val COUNTDOWN_PRESS: Long = (0 * 60 + 50) * 1000 // 00:50

		// "Bars" time for rest
//        const val COUNTDOWN_BARS: Long = (1 * 60 + 50) * 1000 // 01:50

		lateinit var mediaPlayerHolder: MediaPlayerHolder
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		mediaPlayerHolder = MediaPlayerHolder(R.raw.timer_finished)

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
		mediaPlayerHolder.mpRelease()
	}
}
