package com.goforer.lukohsplash.data.repository.remote.photo

import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.data.source.network.worker.NetworkBoundWorker
import com.goforer.lukohsplash.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotoInfoRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<Photo, Photo>(false) {
        override fun request() = restAPI.getPhoto(query.firstParam as String, YOUR_ACCESS_KEY)

        override suspend fun onNetworkError(errorMessage: String, errorCode: Int) {
            handleNetworkError(errorMessage)
        }

    }.asSharedFlow()
}
