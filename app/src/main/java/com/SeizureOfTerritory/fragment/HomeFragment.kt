package com.SeizureOfTerritory.fragment

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.FragmentHomeBinding
import com.SeizureOfTerritory.utils.Utils
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    val WRITE_SETTINGS_PERMISSION_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        initViews()
        return binding.root
    }

    private fun initViews() {

        binding.tvDialogSetting!!.setOnClickListener {
            Utils.showDialogSetting(requireContext(),
                binding.root.fl_home, requireActivity())
        }

        binding.tvDialogResult!!.setOnClickListener {
            Utils.showDialogResult(requireContext(),
                resources.getString(R.string.str_great_result))
        }

        binding.tvDialogRating!!.setOnClickListener { Utils.showDialogRating(requireContext()) }

        binding.tvDialogLose!!.setOnClickListener {
            Utils.showDialogResult(requireContext(),
                resources.getString(R.string.str_you_lose))
        }


    }
}