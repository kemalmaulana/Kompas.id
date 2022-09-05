package com.kemsky.kompas.ui.detail

import androidx.lifecycle.ViewModel
import com.kemsky.kompas.data.model.DetailUserModel
import com.kemsky.kompas.data.model.GithubRepoModel
import com.kemsky.kompas.data.repository.GithubRepository
import com.kemsky.kompas.data.room.GithubDatabase
import com.kemsky.kompas.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val database: GithubDatabase
) : ViewModel() {

    suspend fun fetchDetailUser(username: String): Flow<Resource<DetailUserModel?>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getDetailUser(username)
            if (response.code() == 200 && response.body() != null) {
                response.body()?.let { model ->
                    emit(Resource.Success(model))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (ioe: IOException) {
            emit(Resource.Error(ioe.localizedMessage))
        }
    }

    suspend fun fetchListRepo(username: String): Flow<Resource<List<GithubRepoModel>>> = flow {
        try {
            val response = repository.getListRepo(username)
            if (response.code() == 200 && response.body() != null) {
                response.body()?.let { model ->
                    emit(Resource.Success(model))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (ioe: IOException) {
            emit(Resource.Error(ioe.localizedMessage))
        }
    }

//    suspend fun addRepoToLocal(repo: GithubRepoModel) {
//        if (database.openHelper.writableDatabase.isOpen) {
//            Timber.e("Inserting ${repo.name}")
//            val dao = database.favPokemonDao()
//            viewModelScope.launch(Dispatchers.IO) {
//            dao.insertSingleRepo(repo)
//            }
//        }
//    }
//
//    fun deleteRepoFromLocal(repo: GithubRepoModel) {
//        if (database.openHelper.writableDatabase.isOpen) {
//            val dao = database.favPokemonDao()
//            viewModelScope.launch {
//                dao.deleteRepo(repo)
//            }
//        }
//    }
//
//    fun getSingleRepo(id: Int?): Flow<GithubRepoModel?>? {
//        val dao = database.favPokemonDao()
//        return id?.let { dao.getSingleRepo(it) }
//    }
}