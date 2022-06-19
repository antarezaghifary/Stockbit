package com.stockbit.hiring.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.stockbit.hiring.data.paging.source.WatchlistSource
import com.stockbit.hiring.util.VmData

class WatchlistFactory (
    private val totaltoptiervolfullState: MutableLiveData<VmData<List<DataItem>>>
    ) : DataSource.Factory<Int, DataItem>() {
        override fun create(): DataSource<Int, DataItem> {
            return WatchlistSource(totaltoptiervolfullState)
        }
}