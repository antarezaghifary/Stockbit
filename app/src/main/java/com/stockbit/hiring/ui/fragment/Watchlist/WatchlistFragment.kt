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
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.toast
import com.stockbit.hiring.data.database.totaltop
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

    private var list: totaltop? = null

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
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (dy > 0) {
                        // Scrolling up
                        if(pastVisibleItems+visibleItemCount >= totalItemCount){
                            // End of the list is here.
                            Log.e("TAG", "End of list");
                            progressBottom.visibility = View.VISIBLE
                        }
                    } else {
                        // Scrolling down
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                        // Do something
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        // Do something
                    } else {
                        // Do something
                    }
                }
            })

        }

        setObservableWatchlist()
    }


    private fun setObservableWatchlist() {
        viewModel.totaltoptiervolfullState.observe(viewLifecycleOwner) {
            viewModel.deleteAllData {
                Log.e("TAG", "deleteDataInRoomDB")
            }
            when (it) {
                is VmData.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is VmData.Success -> {
                    if (it.data.isNotEmpty()) {
                        binding.swipeRefresh.isRefreshing = false
                        it.data.forEach {
                            list = totaltop(
                                    it.coinInfo?.name,
                                    it.coinInfo?.fullName,
                                    it.rAW?.uSD?.pRICE,
                                    it.rAW?.uSD?.cHANGEHOUR
                                )
                            viewModel.saveData(
                                list!!
                            ){
                                Log.e("TAG", "setObservableWatchlist: ${list}" )
                            }
                        }

                    }else{

                    }
                }
                is VmData.Empty -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is VmData.Failure -> {
                    toast("${it.message}")
                    binding.swipeRefresh.isRefreshing = false
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
        setObservableWatchlist()
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
        val data = mutableListOf(list)
        watchlistAdapter.addAll(data)
        binding.recyclerView.adapter = watchlistAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun connected() {
        viewModel.initPaging(
            viewLifecycleOwner
        )
        binding.swipeRefresh.isEnabled = true
    }
}