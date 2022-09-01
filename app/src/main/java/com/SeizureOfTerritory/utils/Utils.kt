package com.SeizureOfTerritory.utils

import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.provider.Settings
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat.startActivity
import com.SeizureOfTerritory.R

object Utils {

    fun showDialogSetting(context: Context, linearLayout: LinearLayout,requireActivity: Activity) {
        linearLayout.setBackgroundColor(Color.parseColor("#7F000000"))
        val dialog = Dialog(context, R.style.RoundedDialog)
        dialog.setContentView(R.layout.item_dialog_setting)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT)


        val ivCancel = dialog.findViewById<ImageView>(R.id.iv_cancel)
        val sbBrightness = dialog.findViewById<SeekBar>(R.id.sb_brightness)
        val ivEnglishFlag = dialog.findViewById<ImageView>(R.id.iv_english_flag)
        val ivRussianFlag = dialog.findViewById<ImageView>(R.id.iv_russian_flag)

       sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
           override fun onProgressChanged(seekbar: SeekBar?, currentProgress: Int, p2: Boolean) {
                changeScreenBrightness(currentProgress.toFloat()/100,requireActivity)
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

        dialog.show()
    }

    private fun changeScreenBrightness(level: Float,requireActivity: Activity) {
        val layoutParams: WindowManager.LayoutParams =
            requireActivity.window.attributes


        layoutParams.screenBrightness = level
        requireActivity.window.attributes = layoutParams
    }

    fun showDialogResult(context: Context, result: String) {
        val dialog = Dialog(context, R.style.RoundedDialog2)
        dialog.setContentView(R.layout.item_dialog_result)

        val tvAgain = dialog.findViewById<TextView>(R.id.tv_again)
        val tvResult = dialog.findViewById<TextView>(R.id.tv_result)

        tvResult.text = result
        tvAgain.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showDialogRating(context: Context) {
        val dialog = Dialog(context, R.style.RoundedDialog2)
        dialog.setContentView(R.layout.item_dialog_rating)

        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

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
        object : CountDownTimer(500,500) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                dialog.dismiss()
            }
        }.start()
    }

}

/**
 * Theme_AppCompat_Dialog
 * heme_AppCompat -> ekranni yuqori qismida width match bo'lgan holatda
 * Animation_AppCompat_Tooltip -> ekranni to'liq qoplab olish
 * AlertDialog_AppCompat_Light -> ekranni to'liq qoplab olish
 * Base_V26_Theme_AppCompat_Light -> ekranni to'liq qoplab olish
 */