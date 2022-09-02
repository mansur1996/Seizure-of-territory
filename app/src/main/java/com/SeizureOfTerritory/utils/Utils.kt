package com.SeizureOfTerritory.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.fragment.GameFragment

object Utils {

    var isCheckedSound: Int? = null
    var isCheckedVibration: Int? = null


    /**
     * Dialogs are here
     */

    fun showDialogResult(
        context: Context,
        _result: GameFragment,
        string: String,
        isSwitchOnVibration: Boolean,
    ) {
        val dialog = Dialog(context, R.style.RoundedDialog2)
        dialog.setContentView(R.layout.item_dialog_result)

        val tvAgain = dialog.findViewById<TextView>(R.id.tv_again)
        val tvResult = dialog.findViewById<TextView>(R.id.tv_result)
        val tvMenu = dialog.findViewById<TextView>(R.id.tv_menu)

        dialog.setCancelable(false)

        tvResult.text = string

        tvAgain.setOnClickListener {
            if (isSwitchOnVibration) {
                MediaPlayer.create(context, R.raw.tap_on_the_object).start()
            }
            _result.initViews()
            dialog.dismiss()
        }
        tvMenu.setOnClickListener {
            if (isSwitchOnVibration) {
                MediaPlayer.create(context, R.raw.tap_on_the_object).start()
            }
            _result.backMenu()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showDialogFeedback(context: Context, isVibrationOn: Boolean) {
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

        ivCancel.setOnClickListener {
            if (isVibrationOn) {
                Constants.mediaPlayerVibration.start()
            }
            dialog.dismiss()
        }

        ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (isVibrationOn) {
                    Constants.mediaPlayerVibration.start()
                }
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

    /**
     * The functions which use in fragments
     */
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

    fun isEnabledVibration(context: Context): Boolean {
        val preferences = context.getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)
        return preferences.getBoolean(Constants.KEY_VIBRATION, false)
    }

    fun isEnabledSound(context: Context): Boolean {
        val preferences = context.getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)
        return preferences.getBoolean(Constants.KEY_SOUND, false)
    }
}
