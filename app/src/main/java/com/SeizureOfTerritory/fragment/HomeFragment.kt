package com.SeizureOfTerritory.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.FragmentHomeBinding
import com.SeizureOfTerritory.utils.Constants
import com.SeizureOfTerritory.utils.Constants.mediaPlayer
import com.SeizureOfTerritory.utils.Constants.mediaPlayerVibration
import com.SeizureOfTerritory.utils.Utils
import java.util.*

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var activeLanguage: String? = null
    var isSoundOn: Boolean = false
    var isVibrationOn: Boolean = false
    private var brightness: Float = 50F

    // initialize mediaPlayers here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.menu_game)
        mediaPlayerVibration = MediaPlayer.create(context, R.raw.tap_on_the_object)

        sharedPreferences =
            requireContext().getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        brightness = sharedPreferences.getFloat(Constants.KEY_BRIGHTNESS, 50F)
        manageBrightness(brightness / 100, requireActivity())
        manageLanguage()
        manageSound()
        manageVibration()
    }

    fun manageBrightness(level: Float, requireActivity: Activity) {
        val layoutParams: WindowManager.LayoutParams = requireActivity.window.attributes
        layoutParams.screenBrightness = level
        requireActivity.window.attributes = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.tvStart?.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            findNavController().navigate(R.id.gameFragment)
        }

        binding.tvSetting?.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            showDialogSetting()
        }

        binding.tvFeedback?.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            Utils.showDialogFeedback(requireContext(), isVibrationOn)
        }
    }

    private fun manageLanguage() {
        activeLanguage = sharedPreferences.getString(Constants.KEY_LANG, null)
        if (activeLanguage != null) {
            setLocale(activeLanguage!!)
            Log.d("languageKey", activeLanguage!!)
        }
    }

    private fun manageSound() {
        isSoundOn = Utils.isEnabledSound(requireActivity())
        if (isSoundOn) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }
    }

    private fun manageVibration() {
        isVibrationOn = Utils.isEnabledVibration(requireActivity())
    }

    private fun showDialogSetting() {
        // do background color of homeFragment transparent
        binding.llHome!!.setBackgroundColor(Color.parseColor("#7F000000"))

        // Creating and setting layout off dialog
        val dialog = Dialog(requireContext(), R.style.RoundedDialog)
        dialog.setContentView(R.layout.item_dialog_setting)
        // set flexible size off dialog
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // do background of dialog invisible
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // don't dismiss dialog when click outside of it
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        // initialize values
        val svSound = dialog.findViewById<SwitchCompat>(R.id.sv_sound)
        val svVibration = dialog.findViewById<SwitchCompat>(R.id.sv_vibration)
        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)
        val sbBrightness = dialog.findViewById<SeekBar>(R.id.sb_brightness)
        val ivEnglishFlag = dialog.findViewById<ImageView>(R.id.iv_english_flag)
        val ivRussianFlag = dialog.findViewById<ImageView>(R.id.iv_russian_flag)

        // control languages
        ivRussianFlag.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            val language = "ru"
            editor.putString(Constants.KEY_LANG, language)
            editor.commit()
            setLocale(language)
            dialog.dismiss()
        }
        ivEnglishFlag.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            val language = "en"
            editor.putString(Constants.KEY_LANG, language)
            editor.commit()
            setLocale(language)
            dialog.dismiss()
        }

        // checking sound is playing or not
        svSound.isChecked = sharedPreferences.getBoolean(Constants.KEY_SOUND, false)
        svSound.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                editor.putBoolean(Constants.KEY_SOUND, true)
                editor.commit()
                mediaPlayer.start()
                isSoundOn = true
            } else {
                editor.putBoolean(Constants.KEY_SOUND, false)
                editor.commit()
                mediaPlayer.pause()
                isVibrationOn = false
            }
        }

        // manage Vibration
        svVibration.isChecked = isVibrationOn
        svVibration.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                editor.putBoolean(Constants.KEY_VIBRATION, true)
                editor.commit()
                mediaPlayerVibration.start()
                isVibrationOn = true
            } else {
                editor.putBoolean(Constants.KEY_VIBRATION, false)
                editor.commit()
                isVibrationOn = false
            }
        }

        // control brightness via seekbar
        sbBrightness.progress = sharedPreferences.getFloat(Constants.KEY_BRIGHTNESS,50F).toInt()
        sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekbar: SeekBar?,
                currentProgress: Int,
                p2: Boolean,
            ) {
                manageBrightness(currentProgress.toFloat() / 100, requireActivity())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                editor.putFloat(Constants.KEY_BRIGHTNESS, (p0!!.progress).toFloat())
                editor.commit()
            }

        })

        // dismiss dialog
        ivCancel.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            dialog.dismiss()
            binding.llHome!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.show()
    }

    private fun setLocale(language: String) {
        // Initialize resources
        val resources = resources
        // Initialize metrics
        val metrics = resources.displayMetrics
        // Initialize configuration
        val configuration = resources.configuration
        // Initialize locale
        configuration.locale = Locale(language)
        // Update configuration
        resources.updateConfiguration(configuration, metrics)
        // Notify configuration
        onConfigurationChanged(configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.apply {
            tvStart!!.text = resources.getString(R.string.str_start)
            tvSetting!!.text = resources.getString(R.string.str_setting)
            tvFeedback!!.text = resources.getString(R.string.str_feedback)

        }
    }

}