package com.example.timer

import android.media.MediaPlayer
import android.os.Build
import android.os.PowerManager
import com.example.timer.Utils.Companion.logd
import com.example.timer.Utils.Companion.toast

class MediaPlayerHolder(private val resId: Int) {
	private var mediaPlayer: MediaPlayer? = null


	fun mpStart() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			// Delicate version of starting playing. Not work on KitKat 4.4.2
			if (mediaPlayer == null) {
				mpCreate()
				mediaPlayer!!.start()
				logd("mediaPlayer.start()")
			} else {
				mediaPlayer?.apply {
					stop()
					prepare()
					start()
					logd("mediaPlayer.stop().prepare().start()")
				}
			}
		} else {
			// Aggressive version of starting playing. Work on KitKat 4.4.2
			mpRelease()
			mpCreate()
			mediaPlayer!!.start()
			logd("mediaPlayer.start()")
		}
	}

	private fun mpCreate() {
		mediaPlayer = MediaPlayer.create(App.appContext, resId)
		logd("MediaPlayer.create()")
		mediaPlayer?.apply {
			@Suppress("UNUSED_ANONYMOUS_PARAMETER")
			setOnErrorListener { mp, what, extra ->
				val strError = "MediaPlayer error: what = $what, extra = $extra"
				logd(strError)
				toast(strError)
				reset()
				prepare()
				logd("mediaPlayer.reset().prepare() - On Error")
				// True if the method handled the error, false if it didn't.
				true
			}
			setOnCompletionListener {
				mpRelease()
			}
			setWakeMode(App.appContext, PowerManager.PARTIAL_WAKE_LOCK)
		}
	}

	fun mpRelease() {
		mediaPlayer?.apply {
			reset() // Without it: Warning - mediaPlayer went away with unhandled events
			release()
			logd("mediaPlayer.reset().release()")
		}
		mediaPlayer = null
	}
}