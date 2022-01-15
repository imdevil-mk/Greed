package com.hold.rich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hold.rich.databinding.FragmentBalanceBinding

class BalanceFragment : Fragment() {

    lateinit var binding: FragmentBalanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBalanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: BalanceFragmentArgs by navArgs()
        val flowStepNumber = safeArgs.flowStepNumber
        binding.tips.text = flowStepNumber.toString()
    }
}