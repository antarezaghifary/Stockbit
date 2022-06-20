package com.stockbit.hiring.data.paging.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.oratakashi.viewbinding.core.network.networkSyncReverse
import com.oratakashi.viewbinding.core.tools.retrofit.transformer.composeObservable
import com.stockbit.hiring.data.database.TotalTop
import com.stockbit.hiring.data.database.TotalTopDao
import com.stockbit.hiring.data.repository.UserRepository
import com.stockbit.hiring.util.VmData
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WatchlistSource(
    private val totaltoptiervolfullState: MutableLiveData<VmData<List<TotalTop>>>
) : PageKeyedDataSource<Int, TotalTop>(), KoinComponent {

    private val repository: UserRepository by inject()

    private val dao: TotalTopDao by inject()

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TotalTop>
    ) {
        Log.e("debug", "debug: CALL")

        totaltoptiervolfullState.postValue(VmData.loading())
        networkSyncReverse(
            saveToDb = { dao.addAll(it) },
            fetchDb = { dao.getAll(1) },
            fetchApi = { repository.totalTopTierVolFull(1) },
            mapData = { data ->
                data.map {
                    TotalTop(
                        it.coinInfo?.name,
                        it.coinInfo?.fullName,
                        it.rAW?.uSD?.pRICE,
                        it.rAW?.uSD?.cHANGEHOUR,
                        1,
                        it.coinInfo?.id.orEmpty()
                    )
                }
            }
        )
            .compose(composeObservable())
            .subscribe({

            if (it.isNotEmpty()) {
                    totaltoptiervolfullState.postValue(VmData.success(it))
                    callback.onResult(it, 0, 1)
                } else {
                    totaltoptiervolfullState.postValue(VmData.empty())
                }
            }, {
                it.printStackTrace()
                Log.e("debug", "debug: ${it.message}")
                totaltoptiervolfullState.postValue(VmData.fail(it, it.message))
            })
            .let { return@let compositeDisposable::add }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TotalTop>
    ) {
        //authentification
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TotalTop>
    ) {
        totaltoptiervolfullState.postValue(VmData.loading())
        Log.e("debug", "debug: ${params.key}")
        networkSyncReverse(
            saveToDb = { dao.addAll(it) },
            fetchDb = { dao.getAll(params.key + 1) },
            fetchApi = { repository.totalTopTierVolFull(params.key + 1) },
            mapData = { data ->
                data.map {
                    TotalTop(
                        it.coinInfo?.name,
                        it.coinInfo?.fullName,
                        it.rAW?.uSD?.pRICE,
                        it.rAW?.uSD?.cHANGEHOUR,
                        params.key + 1,
                        it.coinInfo?.id.orEmpty()
                    )
                }
            }
        )
            .compose(composeObservable())
            .subscribe({
                if (it.isNotEmpty()) {
                    totaltoptiervolfullState.postValue(VmData.success(it))
                    callback.onResult(it, params.key + 1)
                } else {
                    totaltoptiervolfullState.postValue(VmData.empty())
                }
            }, {

            })
            .let { return@let compositeDisposable::add }
    }
}
