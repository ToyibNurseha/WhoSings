package com.toyibnurseha.whosings.manager

import android.os.CountDownTimer
import com.toyibnurseha.whosings.interfaces.ITimer

class TimerManager(val duration: Long) {

    private var timer: CountDownTimer? = null
    private var isFinished = false

    fun initTimer(listener: ITimer) {
        timer = object : CountDownTimer(duration, 1000L) {
            override fun onTick(p0: Long) {
                isFinished = false
                listener.onTimerRun(p0)
            }

            override fun onFinish() {
                isFinished = true
                listener.onTimerFinish()
            }

        }
    }

    fun startTimer() {
        isFinished = false
        timer?.start()
    }

    fun cancel() {
        isFinished = true
        timer?.cancel()
    }

    fun isFinished() = isFinished

}