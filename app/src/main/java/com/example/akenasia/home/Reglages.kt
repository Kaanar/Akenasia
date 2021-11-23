package com.example.akenasia.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.reglages.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.R
import com.example.akenasia.databinding.ReglagesBinding


class Reglages: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ReglagesBinding
    private lateinit var appSettingPrefs: SharedPreferences
    private lateinit var sharedPrefsEdit: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReglagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appSettingPrefs= getSharedPreferences("AppSettingPrefs", 0)
        sharedPrefsEdit= appSettingPrefs.edit()

        //setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.include) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        ThemeRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            changeTheme(
                group,
                checkedId
            )
        })

        ReglagesReturnButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }

    fun changeTheme(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            LightRadioButton.id ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
            }
            DarkRadioButton.id ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
            }
            else -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
    }


}