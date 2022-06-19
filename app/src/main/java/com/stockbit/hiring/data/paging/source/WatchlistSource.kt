package com.stockbit.hiring.data.paging.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.oratakashi.viewbinding.core.tools.retrofit.transformer.composeSingle
import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.stockbit.hiring.data.repository.UserRepository
import com.stockbit.hiring.util.VmData
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import retrofit2.HttpException

class WatchlistSource (
    private val totaltoptiervolfullState: MutableLiveData<VmData<List<DataItem>>>
) : PageKeyedDataSource<Int, DataItem>() {

    private val repository: UserRepository by lazy {
        UserRepository()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataItem>
    ) {

        totaltoptiervolfullState.postValue(VmData.loading())
        repository.totalTopTierVolFull(1).compose(composeSingle())
            .subscribe({
                if (it.isNotEmpty()) {
                    totaltoptiervolfullState.postValue(VmData.success(it))
                    callback.onResult(it, 1, 2)
                } else totaltoptiervolfullState.postValue(VmData.empty())
            }, {
                if (it is HttpException) {
                    it.response()?.errorBody()?.string()?.let { response ->
                        val message = JSONObject(response).getString("message")
                        totaltoptiervolfullState.value = VmData.fail(it, message)
                    }
                }
            }).let { return@let compositeDisposable::add }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, DataItem>
    ) {
        //authentification
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, DataItem>
    ) {
        repository.totalTopTierVolFull(params.key).compose(composeSingle())
            .subscribe({
                callback.onResult(it, params.key + 1)
            }, {
                it.printStackTrace()
            }).let { return@let compositeDisposable::add }
    }
}
