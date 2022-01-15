package com.hold.rich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hold.rich.adapters.BalanceListAdapter
import com.hold.rich.databinding.FragmentBalanceBinding
import com.hold.rich.viewmodels.BalanceViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "BalanceFragment"

@AndroidEntryPoint
class BalanceFragment : Fragment() {

    lateinit var binding: FragmentBalanceBinding
    private val balanceViewModel: BalanceViewModel by viewModels()

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
        binding.totalBalance.text = flowStepNumber.toString()

        binding.swipeRefresh.setOnRefreshListener {
            balanceViewModel.refreshBalance()
        }

        val adapter = BalanceListAdapter()
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        binding.list.itemAnimator?.changeDuration = 0

        balanceViewModel.balanceSummary.observe(viewLifecycleOwner) {
            binding.totalBalance.text = it?.totalEq ?: "error"
            adapter.submitList(it?.details ?: emptyList())
            binding.swipeRefresh.isRefreshing = false
        }

        balanceViewModel.refreshBalance()
        binding.swipeRefresh.isRefreshing = true
    }
}