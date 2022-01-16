package com.hold.rich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hold.rich.adapters.OrdersAdapter
import com.hold.rich.databinding.OrdersFragmentBinding
import com.hold.rich.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var root: OrdersFragmentBinding
    private val listAdapter: OrdersAdapter = OrdersAdapter()
    private val safeArgs: OrdersFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchOrders(safeArgs.ccy)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = OrdersFragmentBinding.inflate(layoutInflater, container, false)

        root.list.layoutManager = LinearLayoutManager(requireContext())
        root.list.itemAnimator?.changeDuration = 0
        root.list.adapter = listAdapter

        root.swipeRefresh.setOnRefreshListener {
            viewModel.fetchOrders(safeArgs.ccy)
        }

        return root.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.order.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
            root.swipeRefresh.isRefreshing = false
        }
    }
}