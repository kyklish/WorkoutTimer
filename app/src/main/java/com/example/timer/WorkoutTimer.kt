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

	private lateinit var timer: CountDownTimer

	init {
		textViewTime.text = getTimeFromSeconds(secondsInFuture)

		buttonStart.setOnClickListener {
			it.visibility = View.INVISIBLE
			buttonStop.visibility = View.VISIBLE
			timer.start()
		}

		buttonStop.setOnClickListener {
			it.visibility = View.INVISIBLE
			buttonStart.visibility = View.VISIBLE
			textViewTime.text = getTimeFromSeconds(secondsInFuture)
			textViewTime.setTextColor(Color.WHITE)
			textViewTime.setBackgroundColor(App.appContext!!.resources.getColor(R.color.grey))
			timer.cancel()
		}

		timer = object : CountDownTimer(
			secondsInFuture * ONE_SECOND_IN_MILLIS,
			ONE_SECOND_IN_MILLIS
		) {
			override fun onTick(millisUntilFinished: Long) {
				val secondsUntilFinished = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
				textViewTime.text = getTimeFromSeconds(secondsUntilFinished)
				logd(getTimeFromSeconds(secondsUntilFinished))
			}

			override fun onFinish() {
				textViewTime.apply {
					text = getTimeFromSeconds(0L)
					setBackgroundColor(Color.RED)
					setTextColor(Color.BLACK)
				}
				mediaPlayerHolder.mpStart()
			}
		}
	}
}