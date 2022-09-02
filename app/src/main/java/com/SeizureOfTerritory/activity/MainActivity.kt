package com.SeizureOfTerritory.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.ActivityMainBinding
import com.SeizureOfTerritory.utils.Constants


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var sharedPreferences: SharedPreferences

    var isSoundOn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        initViews()
    }

    private fun initViews() {
        sharedPreferences = this.getSharedPreferences(Constants.PREF_FILE, MODE_PRIVATE)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onPause() {
        super.onPause()
        Constants.mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        isSoundOn = sharedPreferences.getBoolean(Constants.KEY_SOUND, false)
        if (isSoundOn!!) {
            Constants.mediaPlayer.start()
            Constants.mediaPlayer.isLooping = true
        }


    }

}