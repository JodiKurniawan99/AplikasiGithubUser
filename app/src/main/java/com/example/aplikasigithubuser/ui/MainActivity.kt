package com.example.aplikasigithubuser.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.setting.SettingAppPreferences
import com.example.aplikasigithubuser.setting.SettingViewModel
import com.example.aplikasigithubuser.setting.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val bindingMainActivity get() = _activityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity?.root)

        val pref = SettingAppPreferences.getInstance(dataStore)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
            })

        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.left_anim)
        val nameAnimation = AnimationUtils.loadAnimation(this, R.anim.right_anim)

        bindingMainActivity?.imageView?.startAnimation(logoAnimation)
        bindingMainActivity?.textView?.startAnimation(nameAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, ListUserActivity::class.java)
            startActivity(i)
            finish()
        }, DELAY_TIME.toLong())

    }
    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
    companion object{
        const val DELAY_TIME = 3000
    }
}