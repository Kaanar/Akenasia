package com.example.akenasia

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.databinding.ActivityCountdownTimerBinding
import kotlinx.android.synthetic.main.activity_countdown_timer.*
import kotlinx.android.synthetic.main.content_countdown_timer.*
import java.util.*

class CountdownTimer : AppCompatActivity() {

    /*enum class TimerState{
        Stopped, Running
    }

    private lateinit var timer: CountdownTimer
    private var timerLengthSeconds:Long = 0L
    private var timerState = TimerState.Stopped

    private var secondRemaning = 0L*/

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown_timer)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_timer)
        supportActionBar?.title="         Timer"

        /*fab_start.setOnClickListener{ v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        fab_stop.setOnClickListener{ v ->
            timer.cancel()
            onTimerFinished()

        }*/
    }

    fun startTimeCounter(view: View) {
        val countTime: TextView = findViewById(R.id.textView_countdown_timer)
        object : CountDownTimer(50000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = counter.toString()
                counter++
            }
            override fun onFinish() {
                countTime.text = "Finished"
            }
        }.start()
    }



    /*private fun cancel() {

    }

    private fun startTimer() {
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondRemaning*1000, 1000){
            override fun onFinish() {

                override fun onTick(millisUntilFinished: Long) {
                    secondRemaning = millisUntilFinished / 1000
                    updateCoutdownUI()
                }
            }.start()
        }
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondRemaning = timerLengthSeconds

        updateButtons()
        upadateCoutdownUI()
    }*/
}