package com.stockbit.hiring.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.stockbit.hiring.data.database.TotalTop
import com.stockbit.hiring.data.paging.source.WatchlistSource
import com.stockbit.hiring.util.VmData

class WatchlistFactory(
    private val totaltoptiervolfullState: MutableLiveData<VmData<List<TotalTop>>>
) : DataSource.Factory<Int, TotalTop>() {
    override fun create(): DataSource<Int, TotalTop> {
        return WatchlistSource(totaltoptiervolfullState)
    }
}