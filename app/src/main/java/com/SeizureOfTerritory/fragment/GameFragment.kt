package com.SeizureOfTerritory.fragment

import android.annotation.SuppressLint
import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.adapter.MainAdapter
import com.SeizureOfTerritory.databinding.FragmentGameBinding
import com.SeizureOfTerritory.utils.Constants
import com.SeizureOfTerritory.utils.Utils


class GameFragment : Fragment() {
    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }

    private var _moves = 20
    private var _points = 0
    private var isSwitchOnVibration = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        isSwitchOnVibration = Utils.isEnabledVibration(requireActivity())
        initViews()
        return binding.root
    }


    private var coveredMatrixPart = Array(Constants.ROW) { BooleanArray(Constants.COLUMN) }
    private var stageWidth: Int = 0
    fun initViews() {
        /**
         * preparing to start the Game
         */
        fillMatrix(matrix)

        coveredMatrixPart = Utils.fillMatrixWithFalse(coveredMatrixPart)
        coveredMatrixPart[0][0] = true

        stageWidth = getScreenSize()
        setInitialValues(stageWidth / Constants.ROW)

        refreshAdapter(matrix, coveredMatrixPart)

        /**
         * To start the Game
         */
        startGame()
    }

    private fun startGame() {

        binding.apply {
            iv1?.setOnClickListener {
                setClickableFalse(1)
                coverMatrix(iv1Value)
                isWin()
            }
            iv2?.setOnClickListener {
                setClickableFalse(2)
                coverMatrix(iv2Value)
                isWin()
            }
            iv3?.setOnClickListener {
                setClickableFalse(3)
                coverMatrix(iv3Value)
                isWin()
            }
            iv4?.setOnClickListener {
                setClickableFalse(4)
                coverMatrix(iv4Value)
                isWin()
            }
            iv5?.setOnClickListener {
                setClickableFalse(5)
                coverMatrix(iv5Value)
                isWin()
            }
            iv6?.setOnClickListener {
                setClickableFalse(6)
                coverMatrix(iv6Value)
                isWin()
            }
        }
    }

    //fills the matrix with 6 selected images
    private val ballSet = HashSet<Int>()
    private val matrix = Array(Constants.ROW) { IntArray(Constants.COLUMN) }
    private fun fillMatrix(matrix: Array<IntArray>) {
        while (ballSet.size <= Constants.CHOSENBALLS) {
            ballSet.add((1..Constants.ITEMS).shuffled().last())
        }

        for (i in 0 until Constants.ROW) {
            for (j in 0 until Constants.COLUMN) {
                val index = (0 until Constants.CHOSENBALLS).shuffled().last()
                matrix[i][j] = ballSet.elementAt(index)
            }
        }
    }

    //takes the width of the phone screen
    private fun getScreenSize(): Int {
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    //gives value by adjusting the width of the six images to the phone screen
    private fun setInitialValues(width: Int) {
        setImageViewsWidth(width)
        getBallsValue()
        setPointsAndMovies()
    }

    private fun setImageViewsWidth(width: Int) {
        binding.apply {
            iv1!!.layoutParams.width = width
            iv2!!.layoutParams.width = width
            iv3!!.layoutParams.width = width
            iv4!!.layoutParams.width = width
            iv5!!.layoutParams.width = width
            iv6!!.layoutParams.width = width

            iv1.layoutParams.height = width
            iv2.layoutParams.height = width
            iv3.layoutParams.height = width
            iv4.layoutParams.height = width
            iv5.layoutParams.height = width
            iv6.layoutParams.height = width
        }
    }

    private var iv1Value = 0
    private var iv2Value = 0
    private var iv3Value = 0
    private var iv4Value = 0
    private var iv5Value = 0
    private var iv6Value = 0
    private fun getBallsValue() {
        iv1Value = setImage(binding.iv1!!)
        iv2Value = setImage(binding.iv2!!)
        iv3Value = setImage(binding.iv3!!)
        iv4Value = setImage(binding.iv4!!)
        iv5Value = setImage(binding.iv5!!)
        iv6Value = setImage(binding.iv6!!)
    }

    private fun setImage(image: ImageView): Int {
        val ball = ballSet.elementAt(0)

        Utils.setBallToImage(ball, image)

        ballSet.remove(ball)

        // gives a red border to the background of the selected image
        if (matrix[0][0] == ball) {
            image.background = resources.getDrawable(R.drawable.border_gradient_red)
            image.isClickable = false
        }

        return ball
    }

    private fun setPointsAndMovies() {
        _points = Constants.POINTS
        _moves = Constants.MOVES

        binding.tvPoints!!.text = "$_points"
        binding.tvMoves!!.text = "$_moves"
    }

    private fun refreshAdapter(matrix: Array<IntArray>, coveredMatrixPart: Array<BooleanArray>) {
        binding.rvMain!!.adapter = MainAdapter(stageWidth, matrix, coveredMatrixPart)
    }

    // Makes the selected image unselectable again
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setClickableFalse(ball: Int) {
        increaseMovesValue()
        playVibration()

        binding.apply {
            iv1!!.isClickable = true
            iv2!!.isClickable = true
            iv3!!.isClickable = true
            iv4!!.isClickable = true
            iv5!!.isClickable = true
            iv6!!.isClickable = true

            iv1.setBackgroundResource(0)
            iv2.setBackgroundResource(0)
            iv3.setBackgroundResource(0)
            iv4.setBackgroundResource(0)
            iv5.setBackgroundResource(0)
            iv6.setBackgroundResource(0)

            when (ball) {
                1 -> {
                    iv1.isClickable = false
                    iv1.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
                2 -> {
                    iv2.isClickable = false
                    iv2.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
                3 -> {
                    iv3.isClickable = false
                    iv3.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
                4 -> {
                    iv4.isClickable = false
                    iv4.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
                5 -> {
                    iv5.isClickable = false
                    iv5.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
                6 -> {
                    iv6.isClickable = false
                    iv6.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
            }
        }
    }

    private fun playVibration() {
        if (isSwitchOnVibration) MediaPlayer.create(context, R.raw.tap_on_the_object).start()
    }

    private fun increaseMovesValue() {
        if (--_moves >= 0) binding.tvMoves!!.text = "${_moves}"
        else binding.tvMoves!!.text = "0"
    }

    private var first: Int = 0
    private var root = Array(Constants.ROW) { BooleanArray(Constants.COLUMN) }

    //starts filling the matrix with the selected image
    private fun coverMatrix(ivValue: Int) {
        first = matrix[0][0]
        root = Utils.fillMatrixWithFalse(root)
        coveredMatrixPart = Utils.fillMatrixWithFalse(coveredMatrixPart)

        recursion(0, 0, ivValue)

        refreshAdapter(matrix, coveredMatrixPart)
    }

    private fun recursion(i: Int, j: Int, ivValue: Int) {
        if (i < 0 || i >= Constants.ROW || j < 0 || j >= Constants.COLUMN) return

        if (ivValue == first) return

        if (root[i][j]) return

        if (matrix[i][j] == ivValue) {
            root[i][j] = true
            coveredMatrixPart[i][j] = true
            recursion(i, j + 1, ivValue)
            recursion(i + 1, j, ivValue)
            recursion(i, j - 1, ivValue)
            recursion(i - 1, j, ivValue)
        } else if (matrix[i][j] == first) {
            matrix[i][j] = ivValue
            root[i][j] = true
            coveredMatrixPart[i][j] = true
            recursion(i, j + 1, ivValue)
            recursion(i + 1, j, ivValue)
            recursion(i, j - 1, ivValue)
            recursion(i - 1, j, ivValue)
        }
    }

    private fun isWin() {
        var numberOfCoveredCell = 0
        for (i in 0 until Constants.ROW) {
            for (j in 0 until Constants.COLUMN) {
                if (coveredMatrixPart[i][j]) numberOfCoveredCell++
            }
        }

        binding.tvPoints!!.text = "${numberOfCoveredCell * Constants.SCORE}"

        if (numberOfCoveredCell == Constants.ROW * Constants.COLUMN) {
            Utils.showDialogResult(
                requireContext(),
                this,
                resources.getString(R.string.str_great_result),
                isSwitchOnVibration
            )
        } else if (_moves == 0) {
            Utils.showDialogResult(
                requireContext(),
                this,
                resources.getString(R.string.str_you_lose),
                isSwitchOnVibration
            )
        }
    }

    fun backMenu() {
        findNavController().popBackStack(R.id.gameFragment, true)
    }
}