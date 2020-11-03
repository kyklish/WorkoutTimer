package com.example.timer

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.timer.MainActivity.Companion.mediaPlayerHolder
import com.example.timer.Utils.Companion.getTimeFromSeconds
import com.example.timer.Utils.Companion.logd
import java.util.concurrent.TimeUnit

class WorkoutTimer(
	private val secondsInFuture: Long,
	private val textViewTime: TextView,
	private val buttonStart: Button,
	private val buttonStop: Button
) {
	companion object {
		// This is the number of milliseconds in a second
		const val ONE_SECOND_IN_MILLIS = 1000L
	}

	private var timer: CountDownTimer

	init {
		textViewTime.text = getTimeFromSeconds(secondsInFuture)
		buttonStart.setOnClickListener(::startTimer)
		buttonStop.setOnClickListener { stopTimer() }

		timer = object : CountDownTimer(
			// Read comment in "onTick()" method about additional one second here.
			(secondsInFuture + 1) * ONE_SECOND_IN_MILLIS,
			ONE_SECOND_IN_MILLIS
		) {
			override fun onTick(millisUntilFinished: Long) {
				val secondsUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 1
				textViewTime.text = getTimeFromSeconds(secondsUntilFinished)
				logd(getTimeFromSeconds(secondsUntilFinished))
				// Below is code, that must be in "onFinish()". It's workaround lag (approximately
				// one second), that appears, if I do all work in "onFinish()" method. So I set timer
				// to one second longer, and do "finish" work at one second before timer finished.
				if (secondsUntilFinished <= 0) {
					textViewTime.apply {
						text = getTimeFromSeconds(0L)
						setBackgroundColor(Color.RED)
						setTextColor(Color.BLACK)
					}
					mediaPlayerHolder.mpStart()
				}
			}

			override fun onFinish() {}
		}
	}

	@Suppress("UNUSED_PARAMETER")
	private fun startTimer(view: View) {
		buttonStart.visibility = View.INVISIBLE
		buttonStop.visibility = View.VISIBLE
		timer.start()
	}

	fun stopTimer() {
		buttonStop.visibility = View.INVISIBLE
		buttonStart.visibility = View.VISIBLE
		textViewTime.text = getTimeFromSeconds(secondsInFuture)
		textViewTime.setTextColor(Color.WHITE)
		@Suppress("DEPRECATION") // getColor(int id) is deprecated
		textViewTime.setBackgroundColor(App.appContext!!.resources.getColor(R.color.grey))
		timer.cancel()
		mediaPlayerHolder.mpRelease()
	}
}