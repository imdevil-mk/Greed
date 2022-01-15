package com.hold.rich.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hold.rich.api.bean.CoinBalanceSummary
import com.hold.rich.databinding.BalanceListItemBinding

class BalanceListAdapter :
    ListAdapter<CoinBalanceSummary, BalanceListAdapter.ItemHolder>(CoinBalanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            BalanceListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemHolder(
        private val binding: BalanceListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(balance: CoinBalanceSummary) {
            binding.apply {
                binding.ccy.text = balance.ccy
                binding.eqUsd.text = "$" + balance.eqUsd

                binding.eq.text = balance.eq
                binding.frozenBal.text = balance.frozenBal
                binding.availEq.text = balance.availEq
                binding.upl.text = balance.upl
                binding.mgnRatio.text = balance.mgnRatio
                binding.notionalLever.text = balance.notionalLever
            }
        }
    }

    class CoinBalanceDiffCallback : DiffUtil.ItemCallback<CoinBalanceSummary>() {

        override fun areItemsTheSame(
            oldItem: CoinBalanceSummary,
            newItem: CoinBalanceSummary
        ): Boolean {
            return oldItem.ccy == newItem.ccy
        }

        override fun areContentsTheSame(
            oldItem: CoinBalanceSummary,
            newItem: CoinBalanceSummary
        ): Boolean {
            return oldItem == newItem
        }
    }
}