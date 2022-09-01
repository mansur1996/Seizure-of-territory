package com.SeizureOfTerritory.fragment

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.adapter.MainAdapter
import com.SeizureOfTerritory.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var row = 12
    private var column = 12
    private var items = 10
    private var chosenItems = 6

    private var iv1Value = 0
    private var iv2Value = 0
    private var iv3Value = 0
    private var iv4Value = 0
    private var iv5Value = 0
    private var iv6Value = 0

    private val matrix = Array(row) { IntArray(column) }

    private val root = Array(row) { BooleanArray(column) }

    private val coveredMatrixPart = Array(row) { BooleanArray(column) }

    private val ballSet = HashSet<Int>()

    private var stageWidth: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(layoutInflater)

        initViews()
        return binding.root
    }

    private fun initViews() {
        fillMatrix(matrix)
        cleanCoveredMatrixPart()
        getScreenSize()
        setInitialValues(stageWidth / row)
        refreshAdapter(matrix, coveredMatrixPart)

        startGame()
    }

    private fun startGame() {
        binding.apply {
            iv1.setOnClickListener {
                coverMatrix( iv1Value)
                setClickableFalse(1)
            }
            iv2.setOnClickListener {
                coverMatrix( iv2Value)
                setClickableFalse(2)
            }
            iv3.setOnClickListener {
                coverMatrix( iv3Value)
                setClickableFalse(3)
            }
            iv4.setOnClickListener {
                coverMatrix( iv4Value)
                setClickableFalse(4)
            }
            iv5.setOnClickListener {
                coverMatrix( iv5Value)
                setClickableFalse(5)
            }
            iv6.setOnClickListener {
                coverMatrix( iv6Value)
                setClickableFalse(6)
            }
        }
    }

    private var first: Int = 0;
    //starts filling the matrix with the selected image
    private fun coverMatrix( ivValue: Int) {
        first = matrix[0][0]
        cleanRoot()
        cleanCoveredMatrixPart()

        recursion(0, 0, ivValue)

        refreshAdapter(matrix, coveredMatrixPart)

        isWin()
    }

    private fun isWin() {
        for (i in 0 until row) {
            for (j in 0 until column) {
                if(!coveredMatrixPart[i][j]) return
            }
        }
        Toast.makeText(requireContext(), "Win", Toast.LENGTH_SHORT).show()
    }

    private fun recursion(i: Int, j: Int, ivValue: Int) {
        if (i < 0 || i >= row || j < 0 || j >= column) return;

        if (ivValue == first) return;

        if (root[i][j]) return;

        if (matrix[i][j] == ivValue) {
            root[i][j] = true;
            coveredMatrixPart[i][j] = true
            recursion(i, j + 1, ivValue);
            recursion(i + 1, j, ivValue);
            recursion(i, j - 1, ivValue);
            recursion(i - 1, j, ivValue);
        } else if (matrix[i][j] == first) {
            matrix[i][j] = ivValue;
            root[i][j] = true;
            coveredMatrixPart[i][j] = true
            recursion(i, j + 1, ivValue);
            recursion(i + 1, j, ivValue);
            recursion(i, j - 1, ivValue);
            recursion(i - 1, j, ivValue);
        }
    }

    //fills the matrix with 6 selected images
    private fun fillMatrix(matrix: Array<IntArray>) {
        while (ballSet.size <= chosenItems) {
            ballSet.add((1..items).shuffled().last())
        }

        for (i in 0 until row) {
            for (j in 0 until column) {
                val index = (0 until chosenItems).shuffled().last()
                matrix[i][j] = ballSet.elementAt(index)
            }
        }
    }

    private fun cleanRoot() {
        for (i in 0 until row) {
            for (j in 0 until column) {
                root[i][j] = false
            }
        }
    }

    private fun cleanCoveredMatrixPart() {
        for (i in 0 until row) {
            for (j in 0 until column) {
                coveredMatrixPart[i][j] = false
            }
        }
    }

    private fun refreshAdapter(matrix: Array<IntArray>, coveredMatrixPart: Array<BooleanArray>,) {
        binding.rvMain.adapter = MainAdapter(stageWidth, matrix, coveredMatrixPart)
    }

    //takes the width of the phone screen
    private fun getScreenSize(){
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        stageWidth = size.y
    }

    //gives value by adjusting the width of the six images to the phone screen
    private fun setInitialValues(width: Int) {
        binding.apply {
            iv1.layoutParams.width = width
            iv2.layoutParams.width = width
            iv3.layoutParams.width = width
            iv4.layoutParams.width = width
            iv5.layoutParams.width = width
            iv6.layoutParams.width = width

            iv1.layoutParams.height = width
            iv2.layoutParams.height = width
            iv3.layoutParams.height = width
            iv4.layoutParams.height = width
            iv5.layoutParams.height = width
            iv6.layoutParams.height = width

            iv1Value = setImage(iv1)
            iv2Value = setImage(iv2)
            iv3Value = setImage(iv3)
            iv4Value = setImage(iv4)
            iv5Value = setImage(iv5)
            iv6Value = setImage(iv6)
        }
    }

    //Chooses 6 out of 10 pictures
    private fun setImage(image: ImageView): Int {
        val chosenBall = matrix[0][0]

        val element = ballSet.elementAt(0)
        when (element) {
            1 -> {
                image.setImageResource(R.mipmap.ic_ball1)
            }
            2 -> {
                image.setImageResource(R.mipmap.ic_ball2)
            }
            3 -> {
                image.setImageResource(R.mipmap.ic_ball3)
            }
            4 -> {
                image.setImageResource(R.mipmap.ic_ball4)
            }
            5 -> {
                image.setImageResource(R.mipmap.ic_ball5)
            }
            6 -> {
                image.setImageResource(R.mipmap.ic_ball6)
            }
            7 -> {
                image.setImageResource(R.mipmap.ic_ball7)
            }
            8 -> {
                image.setImageResource(R.mipmap.ic_ball8)
            }
            9 -> {
                image.setImageResource(R.mipmap.ic_ball9)
            }
            10 -> {
                image.setImageResource(R.mipmap.ic_ball10)
            }
        }
        ballSet.remove(element)


        // gives a red border to the background of the selected image
        if (chosenBall == element) {
            image.background = resources.getDrawable(R.drawable.border_gradient_red)
            image.isClickable = false
        }

        return element
    }

    // Makes the selected image unselectable again
    private fun setClickableFalse(i: Int) {
        binding.apply {
            when (i) {
                1 -> {
                    iv1.isClickable = false
                    iv2.isClickable = true
                    iv3.isClickable = true
                    iv4.isClickable = true
                    iv5.isClickable = true
                    iv6.isClickable = true

                    iv1.background = resources.getDrawable(R.drawable.border_gradient_red)
                    iv2.setBackgroundResource(0)
                    iv3.setBackgroundResource(0)
                    iv4.setBackgroundResource(0)
                    iv5.setBackgroundResource(0)
                    iv6.setBackgroundResource(0)
                }
                2 -> {
                    iv1.isClickable = true
                    iv2.isClickable = false
                    iv3.isClickable = true
                    iv4.isClickable = true
                    iv5.isClickable = true
                    iv6.isClickable = true

                    iv1.setBackgroundResource(0)
                    iv2.background = resources.getDrawable(R.drawable.border_gradient_red)
                    iv3.setBackgroundResource(0)
                    iv4.setBackgroundResource(0)
                    iv5.setBackgroundResource(0)
                    iv6.setBackgroundResource(0)
                }
                3 -> {
                    iv1.isClickable = true
                    iv2.isClickable = true
                    iv3.isClickable = false
                    iv4.isClickable = true
                    iv5.isClickable = true
                    iv6.isClickable = true

                    iv1.setBackgroundResource(0)
                    iv2.setBackgroundResource(0)
                    iv3.background = resources.getDrawable(R.drawable.border_gradient_red)
                    iv4.setBackgroundResource(0)
                    iv5.setBackgroundResource(0)
                    iv6.setBackgroundResource(0)
                }
                4 -> {
                    iv1.isClickable = true
                    iv2.isClickable = true
                    iv3.isClickable = true
                    iv4.isClickable = false
                    iv5.isClickable = true
                    iv6.isClickable = true

                    iv1.setBackgroundResource(0)
                    iv2.setBackgroundResource(0)
                    iv3.setBackgroundResource(0)
                    iv4.background = resources.getDrawable(R.drawable.border_gradient_red)
                    iv5.setBackgroundResource(0)
                    iv6.setBackgroundResource(0)
                }
                5 -> {
                    iv1.isClickable = true
                    iv2.isClickable = true
                    iv3.isClickable = true
                    iv4.isClickable = true
                    iv5.isClickable = false
                    iv6.isClickable = true

                    iv1.setBackgroundResource(0)
                    iv2.setBackgroundResource(0)
                    iv3.setBackgroundResource(0)
                    iv4.setBackgroundResource(0)
                    iv5.background = resources.getDrawable(R.drawable.border_gradient_red)
                    iv6.setBackgroundResource(0)
                }
                6 -> {
                    iv1.isClickable = true
                    iv2.isClickable = true
                    iv3.isClickable = true
                    iv4.isClickable = true
                    iv5.isClickable = true
                    iv6.isClickable = false

                    iv1.setBackgroundResource(0)
                    iv2.setBackgroundResource(0)
                    iv3.setBackgroundResource(0)
                    iv4.setBackgroundResource(0)
                    iv5.setBackgroundResource(0)
                    iv6.background = resources.getDrawable(R.drawable.border_gradient_red)
                }
            }
        }
    }

}