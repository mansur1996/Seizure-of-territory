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
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
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

    // initialize mediaPlayers here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        initViews()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.menu_game)
        mediaPlayerVibration = MediaPlayer.create(context, R.raw.tap_on_the_object)

        sharedPreferences = requireContext().getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        manageLanguage()
        manageSound()
    }

    private fun initViews() {


        binding.tvStart?.setOnClickListener {
            if (isVibrationOn) {
                mediaPlayerVibration.start()
            }
            findNavController().navigate(R.id.gameFragment)
        }

        binding.tvSetting?.setOnClickListener {
            showDialogSetting()
        }

        binding.tvFeedback?.setOnClickListener {
            Utils.showDialogFeedback(requireContext())
        }
    }

    private fun manageLanguage() {
        activeLanguage = sharedPreferences.getString(Constants.KEY_LANG, null)
        if (activeLanguage != null) {
            setLocale(activeLanguage!!)
            Log.d("languageKey", activeLanguage!!)
        }
    }

    private fun manageSound(): Boolean {
        isSoundOn = sharedPreferences.getBoolean(Constants.KEY_SOUND, false)
        if (isSoundOn) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        } else {
            mediaPlayer.pause()
        }
        return isSoundOn
    }

    private fun manageVibration() {
        isVibrationOn = sharedPreferences.getBoolean(Constants.KEY_VIBRATION, false)
        if (isVibrationOn) {
            mediaPlayerVibration.start()
        }
    }

    private fun showDialogSetting() {

        // do background color of homeFragment transparent
        binding.llHome!!.setBackgroundColor(Color.parseColor("#7F000000"))

        // Creating and setting layout off dialog
        val dialog = Dialog(requireContext(), R.style.RoundedDialog)
        dialog.setContentView(R.layout.item_dialog_setting)

        // initialize values
        val svSound = dialog.findViewById<SwitchCompat>(R.id.sv_sound)
        val svVibration = dialog.findViewById<SwitchCompat>(R.id.sv_vibration)
        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)
        val sbBrightness = dialog.findViewById<SeekBar>(R.id.sb_brightness)
        val ivEnglishFlag = dialog.findViewById<ImageView>(R.id.iv_english_flag)
        val ivRussianFlag = dialog.findViewById<ImageView>(R.id.iv_russian_flag)

        // set flexible size off dialog
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT)


        // control languages
        ivRussianFlag.setOnClickListener {
            val language = "ru"
            editor.putString(Constants.KEY_LANG, language)
            editor.commit()
            setLocale(language)
            dialog.dismiss()
        }
        ivEnglishFlag.setOnClickListener {
            val language = "en"
            editor.putString(Constants.KEY_LANG,language)
            editor.commit()
            setLocale(language)
            dialog.dismiss()
        }

        val isSoundOn2 = manageSound()
        isVibrationOn = sharedPreferences.getBoolean(Constants.KEY_VIBRATION, false)

        // checking sound playing or not
        svSound.isChecked = isSoundOn2

        svSound.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    Toast.makeText(requireContext(), "ON", Toast.LENGTH_SHORT).show()
                    mediaPlayer.start()
                    editor.putBoolean(Constants.KEY_SOUND, true)
                    editor.commit()
                } else {
                    Toast.makeText(requireContext(), "OFF", Toast.LENGTH_SHORT).show()
                    mediaPlayer.pause()
                    editor.putBoolean(Constants.KEY_SOUND, false)
                    editor.commit()
                }
            }

        })

        svVibration.isChecked = isVibrationOn

        svVibration.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    editor.putBoolean(Constants.KEY_VIBRATION, true)
                    editor.commit()
                    mediaPlayerVibration.start()
                } else {
                    editor.putBoolean(Constants.KEY_VIBRATION, false)
                    editor.commit()
                    mediaPlayerVibration.pause()
                }
            }

        })

        // don't dismiss dialog when click outside of it
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        // control brightness via seekbar
        sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekbar: SeekBar?,
                currentProgress: Int,
                p2: Boolean,
            ) {
                changeScreenBrightness(currentProgress.toFloat() / 100, requireActivity())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        // dismiss dialog
        ivCancel.setOnClickListener {
            dialog.dismiss()
            binding.llHome!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // do background of dialog invisible
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


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

    fun changeScreenBrightness(level: Float, requireActivity: Activity) {
        val layoutParams: WindowManager.LayoutParams = requireActivity.window.attributes
        layoutParams.screenBrightness = level
        requireActivity.window.attributes = layoutParams
    }


}