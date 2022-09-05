package com.kemsky.kompas.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kemsky.kompas.data.repository.GithubRepository
import com.kemsky.kompas.data.room.GithubDatabase
import com.kemsky.kompas.ui.main.adapter.AllUserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val database: GithubDatabase
) : ViewModel() {

    val listUserData = Pager(PagingConfig(pageSize = 30)) {
        AllUserDataSource(repository, database)
    }.flow.cachedIn(viewModelScope)

//    suspend fun fetchListUser(): Flow<Resource<List<UserModel>?>> = flow {
//        emit(Resource.Loading())
//        try {
//            val response = repository.getListUser(0)
//            if (response.code() == 200 && response.body() != null) {
//                response.body()?.let { model ->
//                    emit(Resource.Success(model))
//                }
//            } else {
//                emit(Resource.Error(response.message()))
//            }
//        } catch (ioe: IOException) {
//            emit(Resource.Error(ioe.localizedMessage))
//        }
//    }

}