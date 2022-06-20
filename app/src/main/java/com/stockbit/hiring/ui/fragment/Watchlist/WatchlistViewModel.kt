package com.stockbit.hiring.ui.fragment.Watchlist.Watchlist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.stockbit.hiring.data.database.TotalTop
import com.stockbit.hiring.data.paging.factory.WatchlistFactory
import com.stockbit.hiring.util.VmData

class WatchlistViewModel: ViewModel(){

    val totaltoptiervolfullState: MutableLiveData<VmData<List<TotalTop>>> by liveData(VmData.default())

    val data: MutableLiveData<PagedList<TotalTop>> by liveData()

    fun initPaging(
        lifecycleOwner: LifecycleOwner
    ) {
        LivePagedListBuilder(
            WatchlistFactory(totaltoptiervolfullState),
            50
        ).build().observe(lifecycleOwner, data::postValue)
    }

}