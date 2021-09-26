package com.example.akenasia

import android.app.Application
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.akenasia.databinding.ActivityMainBinding
import com.example.akenasia.Position


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var pos: Position


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pos = Position(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        findViewById<TextView>(R.id.textview_XCoordonnees_Current_Value).text = "0"
        findViewById<TextView>(R.id.textview_YCoordonnees_Current_Value).text = "0"
        findViewById<TextView>(R.id.textview_XCoordonnees_Goals_Value).text = "48.890900"
        findViewById<TextView>(R.id.textview_YCoordonnees_Goals_Value).text = "2.209300"

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

            findViewById<Button>(R.id.buttontest).setOnClickListener{
            //val pos = Position()
            pos.refreshLocation()
            findViewById<TextView>(R.id.textview_XCoordonnees_Current_Value).text =pos.getLatitude().toString()
            findViewById<TextView>(R.id.textview_YCoordonnees_Current_Value).text = pos.getLongitude().toString()
            //Toast.makeText(applicationContext,"test", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}