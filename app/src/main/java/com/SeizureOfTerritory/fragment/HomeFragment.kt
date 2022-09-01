package com.SeizureOfTerritory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.FragmentHomeBinding
import com.SeizureOfTerritory.utils.Utils
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

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


        binding.tvStart?.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }
    }
}