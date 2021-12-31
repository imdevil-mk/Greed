package com.hold.rich

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hold.rich.api.bean.SupportCoin
import com.hold.rich.databinding.SupportCoinListItemBinding

private const val TAG = "SupportCoinAdapter"

class SupportCoinAdapter :
    ListAdapter<SupportCoin, SupportCoinAdapter.ItemHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            SupportCoinListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        holder.bind(getItem(position))
    }

    class ItemHolder(
        private val binding: SupportCoinListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: SupportCoin) {
            Log.d(TAG, "bind: ${coin.name}")
            binding.name.text = coin.ccy + " " + coin.chain
        }
    }

    class CoinDiffCallback : DiffUtil.ItemCallback<SupportCoin>() {
        override fun areItemsTheSame(oldItem: SupportCoin, newItem: SupportCoin): Boolean {
            return oldItem.ccy == newItem.ccy
        }

        override fun areContentsTheSame(oldItem: SupportCoin, newItem: SupportCoin): Boolean {
            return oldItem == newItem
        }
    }
}