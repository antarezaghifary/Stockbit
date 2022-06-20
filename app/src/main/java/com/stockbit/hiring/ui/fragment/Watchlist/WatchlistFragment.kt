package com.stockbit.hiring.ui.fragment.Watchlist.Watchlist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.toast
import com.stockbit.hiring.ui.fragment.Watchlist.WatchlistAdaper
import com.stockbit.hiring.util.CustomView.showCustomToast
import com.stockbit.hiring.util.VmData
import com.test.stockbit.databinding.FragmentWatchlistBinding


class WatchlistFragment : Fragment() {

    private val binding: FragmentWatchlistBinding by viewBinding()
    private val viewModel: WatchlistViewModel by viewModels()
    private val watchlistAdapter: WatchlistAdaper by lazy{
        WatchlistAdaper()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            viewModel.initPaging(
                viewLifecycleOwner
            )

            swipeRefresh.setOnRefreshListener {
                viewModel.initPaging(
                    viewLifecycleOwner
                )
                swipeRefresh.isRefreshing = false
            }
        }
    }


    private fun setObservableWatchlist() {
        viewModel.totaltoptiervolfullState.observe(viewLifecycleOwner) {
            when (it) {
                is VmData.Loading -> {
                    Log.e("TAG", "setObservableWatchlist: ")
                    toast("Loading . . .")
                    binding.swipeRefresh.isRefreshing = true
                }
                is VmData.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is VmData.Empty -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is VmData.Failure -> {
                    toast("${it.message}")
                    binding.swipeRefresh.isRefreshing = false
                }
                is VmData.LoadMore -> {
                    toast("Tunggu . . .")
                }
            }

            viewModel.data.observe(viewLifecycleOwner, watchlistAdapter::submitList)
            binding.recyclerView.adapter = watchlistAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }
    override fun onStart() {
        super.onStart()
        context?.registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(broadcastReceiver)
    }
    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                .EXTRA_NO_CONNECTIVITY, false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }
    private fun disconnected() {
        Toast(requireContext()).showCustomToast(
            "No connetion internet. . .",
            requireActivity()
        )
        binding.swipeRefresh.isEnabled = false
    }

    private fun connected() {
        viewModel.initPaging(
            viewLifecycleOwner
        )
        binding.swipeRefresh.isEnabled = true
        setObservableWatchlist()
    }
}