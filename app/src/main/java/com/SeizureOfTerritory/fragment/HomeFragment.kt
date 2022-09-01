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
        binding.tvStart?.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }

        binding.tvSetting?.setOnClickListener {
            Utils.showDialogSetting(requireContext(),binding.llHome!!, requireActivity())
        }

        binding.tvFeedback?.setOnClickListener {
            Utils.showDialogFeedback(requireContext())
        }
    }
}