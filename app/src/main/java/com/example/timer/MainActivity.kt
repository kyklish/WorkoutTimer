package com.example.timer

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : Activity() {

	companion object {
		lateinit var mediaPlayerHolder: MediaPlayerHolder
		lateinit var timers: List<WorkoutTimer>
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		findViewById<Button>(R.id.buttonStopAll).setOnClickListener(::stopTimers)
		mediaPlayerHolder = MediaPlayerHolder(R.raw.timer_finished)
		timers = createTimers()
	}

	override fun onStop() {
		super.onStop()
		mediaPlayerHolder.mpRelease()
	}

	private fun createTimers(): List<WorkoutTimer> {
		return listOf(
			WorkoutTimer(
				BuildConfig.COUNTDOWN_PULL_UP,
				findViewById(R.id.textPullUp1),
				findViewById(R.id.buttonPullUpStart1),
				findViewById(R.id.buttonPullUpStop1)
			),
			WorkoutTimer(
				BuildConfig.COUNTDOWN_PULL_UP,
				findViewById(R.id.textPullUp2),
				findViewById(R.id.buttonPullUpStart2),
				findViewById(R.id.buttonPullUpStop2)
			),
			WorkoutTimer(
				BuildConfig.COUNTDOWN_PRESS,
				findViewById(R.id.textPress1),
				findViewById(R.id.buttonPressStart1),
				findViewById(R.id.buttonPressStop1)
			),
			WorkoutTimer(
				BuildConfig.COUNTDOWN_PRESS,
				findViewById(R.id.textPress2),
				findViewById(R.id.buttonPressStart2),
				findViewById(R.id.buttonPressStop2)
			),
			WorkoutTimer(
				BuildConfig.COUNTDOWN_BARS,
				findViewById(R.id.textBars2),
				findViewById(R.id.buttonBarsStart2),
				findViewById(R.id.buttonBarsStop2)
			),
			WorkoutTimer(
				BuildConfig.COUNTDOWN_BARS,
				findViewById(R.id.textBars1),
				findViewById(R.id.buttonBarsStart1),
				findViewById(R.id.buttonBarsStop1)
			)
		)
	}

	@Suppress("UNUSED_PARAMETER")
	fun stopTimers(view: View) {
		for (timer in timers) {
			timer.stopTimer()
		}
	}
}
