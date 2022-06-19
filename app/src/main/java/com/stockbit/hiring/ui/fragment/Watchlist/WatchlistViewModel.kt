package com.stockbit.hiring.ui.fragment.Watchlist.Watchlist

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.oratakashi.viewbinding.core.binding.livedata.liveData
import com.oratakashi.viewbinding.core.tools.retrofit.transformer.composeSingle
import com.stockbit.hiring.data.database.Database
import com.stockbit.hiring.data.database.totaltop
import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.stockbit.hiring.data.paging.factory.WatchlistFactory
import com.stockbit.hiring.util.MyApp
import com.stockbit.hiring.util.VmData
import io.reactivex.disposables.CompositeDisposable

class WatchlistViewModel: ViewModel(){

    val totaltoptiervolfullState: MutableLiveData<VmData<List<DataItem>>> by liveData(VmData.default())

    val data: MutableLiveData<PagedList<DataItem>> by liveData()

    private val database: Database by lazy {
        Database.getInstance(MyApp.context!!)
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun initPaging(
        lifecycleOwner: LifecycleOwner
    ) {
        LivePagedListBuilder(
            WatchlistFactory(totaltoptiervolfullState),
            10
        ).build().observe(lifecycleOwner, data::postValue)
    }

    fun saveData(data: totaltop?, doOnComplete: () -> Unit) {
        database.totalTopDao().add(data)
            .compose(composeSingle())
            .subscribe({
                doOnComplete.invoke()
            }, {

            }).let { return@let compositeDisposable::add }
    }
    fun deleteAllData(doOnComplete: () -> Unit) {
        database.totalTopDao().deleteAllData()
            .compose(composeSingle())
            .subscribe({
                doOnComplete.invoke()
            }, {
                it.printStackTrace()
                Log.e("TAG", "delete: ${it.message}")
            }).let { return@let compositeDisposable::add }
    }

}