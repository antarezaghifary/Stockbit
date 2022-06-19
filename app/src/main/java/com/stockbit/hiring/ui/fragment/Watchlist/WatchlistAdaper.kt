package com.stockbit.hiring.ui.fragment.Watchlist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.stockbit.hiring.data.database.totaltop
import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.test.stockbit.databinding.ItemListBinding

class WatchlistAdaper (
) : PagedListAdapter<DataItem, ViewHolder<ItemListBinding>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemListBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ItemListBinding>, position: Int) {
        with(holder.binding) {
            getItem(position)?.let {
                tvName.text = it.coinInfo?.name
                tvNamePt.text = it.coinInfo?.fullName
                tvPrice.text = it.rAW?.uSD?.pRICE
                tvIncrease.text = it.rAW?.uSD?.cHANGEHOUR
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.coinInfo?.id == newItem.coinInfo?.id
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: MutableList<totaltop?>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    private val data: MutableList<totaltop?> by lazy {
        ArrayList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }
}