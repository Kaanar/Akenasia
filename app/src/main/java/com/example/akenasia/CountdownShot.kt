package com.example.akenasia

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.databinding.ActivityCountdownShotBinding

class CountdownShot : AppCompatActivity() {

    private lateinit var counter_txt : TextView
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown_shot)


        counter_txt = findViewById(R.id.counter_txt)
        button = findViewById(R.id.btn_click)

        var  timesCliked = 10

        button.setOnClickListener {

            timesCliked -= 1

            counter_txt.text = timesCliked.toString()

        }
    }

}