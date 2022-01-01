package com.hold.rich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hold.rich.api.bean.Order
import com.hold.rich.databinding.SupportCoinListItemBinding
import com.hold.rich.utils.unixTimeToString

private const val TAG = "SupportCoinAdapter"

class OrdersAdapter :
    ListAdapter<Order, OrdersAdapter.ItemHolder>(CoinDiffCallback()) {

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
        holder.bind(getItem(position))
    }

    class ItemHolder(
        private val binding: SupportCoinListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.apply {
                instId.text = order.instId
                state.text = order.state
                side.text = order.side
                fillTime.text = unixTimeToString(order.fillTime)
                sz.text = order.sz
                px.text = order.px
                fillSz.text = order.fillSz
                avgPx.text = order.avgPx
            }
        }
    }

    class CoinDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.ordId == newItem.ordId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}