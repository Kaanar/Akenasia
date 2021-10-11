package com.example.akenasia

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.homepage.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataDb: DatabaseHandler
    private lateinit var placeListView: ListView
    private lateinit var placeAdapter: ArrayAdapter<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
        setSupportActionBar(binding.toolbar)



        /*val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        HomepageJouerBT.setOnClickListener {
            navController.navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
*/
        /*placeListView = findViewById<View>(R.id.listView) as ListView
        placeAdapter =
            RencontreAdapter(applicationContext, rencontres, Intent().putExtra("UserId", IdUser))
        rencontresListView.setAdapter(rencontreAdapter)*/

    }
}