package com.SeizureOfTerritory.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.tvStart.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }
    }
}