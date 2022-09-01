package com.SeizureOfTerritory.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.fragment.HomeFragment
import com.SeizureOfTerritory.fragment.GameFragment

object Utils {

    var isCheckedSound: Int? = null
    var isCheckedVibration: Int? = null

    fun showDialogSetting(context: Context, linearLayout: LinearLayout, requireActivity: Activity) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.menu_game)
        val mediaPlayerVibration = MediaPlayer.create(context, R.raw.tap_on_the_object)


        linearLayout.setBackgroundColor(Color.parseColor("#7F000000"))
        val dialog = Dialog(context, R.style.RoundedDialog)
        dialog.setContentView(R.layout.item_dialog_setting)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val svSound = dialog.findViewById<SwitchCompat>(R.id.sv_sound)
        val svVibration = dialog.findViewById<SwitchCompat>(R.id.sv_vibration)
        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)
        val sbBrightness = dialog.findViewById<SeekBar>(R.id.sb_brightness)
        val ivEnglishFlag = dialog.findViewById<ImageView>(R.id.iv_english_flag)
        val ivRussianFlag = dialog.findViewById<ImageView>(R.id.iv_russian_flag)

        if (isCheckedSound == 1 && !mediaPlayer.isPlaying) {
            svSound.isChecked = true

            mediaPlayer.start()
        } else {
            svSound.isChecked = false
        }

        if (isCheckedVibration == 1) {
            mediaPlayerVibration.start()
            svVibration.isChecked = true
        } else {
            svVibration.isChecked = false
        }

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekbar: SeekBar?,
                currentProgress: Int,
                p2: Boolean
            ) {
                changeScreenBrightness(currentProgress.toFloat() / 100, requireActivity)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        ivCancel.setOnClickListener {
            dialog.dismiss()
            linearLayout.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        svSound.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    mediaPlayer.start()
                    isCheckedSound = 1
                } else {
                    isCheckedSound = 0
                    mediaPlayer.pause()
                }
            }

        })


        svVibration.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    isCheckedVibration = 1
                    mediaPlayerVibration.start()
                } else {
                    isCheckedVibration = 0
                    mediaPlayerVibration.pause()
                }
            }

        })


        dialog.show()
    }

    private fun changeScreenBrightness(level: Float, requireActivity: Activity) {
        val layoutParams: WindowManager.LayoutParams =
            requireActivity.window.attributes


        layoutParams.screenBrightness = level
        requireActivity.window.attributes = layoutParams
    }

    fun showDialogResult(context: Context, _result: GameFragment, string: String) {
        val dialog = Dialog(context, R.style.RoundedDialog2)
        dialog.setContentView(R.layout.item_dialog_result)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        val tvAgain = dialog.findViewById<TextView>(R.id.tv_again)
        val tvResult = dialog.findViewById<TextView>(R.id.tv_result)
        val tvMenu = dialog.findViewById<TextView>(R.id.tv_menu)

        tvResult.text = string

        tvAgain.setOnClickListener {
            _result.initViews()
            dialog.dismiss()
        }
        tvMenu.setOnClickListener {
            _result.backMenu()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showDialogFeedback(context: Context) {
        val dialog = Dialog(context, R.style.RoundedDialog2)
        dialog.setContentView(R.layout.item_dialog_rating)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)
        val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)

        ivCancel.setOnClickListener { dialog.dismiss() }
        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                countDownTimer(dialog)
            }

        dialog.show()
    }

    private fun countDownTimer(dialog: Dialog) {
        object : CountDownTimer(500, 500) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                dialog.dismiss()
            }
        }.start()
    }

    // Fragment
    fun fillMatrixWithFalse(matrix: Array<BooleanArray>): Array<BooleanArray> {
        for (i in 0 until Constants.ROW) {
            for (j in 0 until Constants.COLUMN) {
                matrix[i][j] = false
            }
        }
        return matrix
    }

    fun setBallToImage(item: Int, imageView: ImageView) {
        when (item) {
            1 -> {
                imageView.setImageResource(R.mipmap.ic_ball1)
            }
            2 -> {
                imageView.setImageResource(R.mipmap.ic_ball2)
            }
            3 -> {
                imageView.setImageResource(R.mipmap.ic_ball3)
            }
            4 -> {
                imageView.setImageResource(R.mipmap.ic_ball4)
            }
            5 -> {
                imageView.setImageResource(R.mipmap.ic_ball5)
            }
            6 -> {
                imageView.setImageResource(R.mipmap.ic_ball6)
            }
            7 -> {
                imageView.setImageResource(R.mipmap.ic_ball7)
            }
            8 -> {
                imageView.setImageResource(R.mipmap.ic_ball8)
            }
            9 -> {
                imageView.setImageResource(R.mipmap.ic_ball9)
            }
            10 -> {
                imageView.setImageResource(R.mipmap.ic_ball10)
            }
        }
    }
}