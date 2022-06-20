package com.stockbit.hiring.ui.fragment.Watchlist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.stockbit.hiring.data.database.TotalTop
import com.test.stockbit.databinding.ItemListBinding

class WatchlistAdaper : PagedListAdapter<TotalTop, ViewHolder<ItemListBinding>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ItemListBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ItemListBinding>, position: Int) {
        with(holder.binding) {
            getItem(position)?.let {
                tvName.text = it.name
                tvNamePt.text = it.fullname
                tvPrice.text = it.price
                tvIncrease.text = it.changeHouse
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TotalTop>() {
            override fun areItemsTheSame(oldItem: TotalTop, newItem: TotalTop): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TotalTop, newItem: TotalTop): Boolean {
                return (oldItem.id == newItem.id) &&
                        (oldItem.price == newItem.price) &&
                        (oldItem.name == newItem.name) &&
                        (oldItem.changeHouse == newItem.changeHouse) &&
                        (oldItem.fullname == newItem.fullname)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: MutableList<TotalTop?>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    private val data: MutableList<TotalTop?> by lazy {
        ArrayList()
    }
}