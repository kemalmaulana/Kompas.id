package com.kemsky.kompas.ui.main.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kemsky.kompas.data.model.UserModel
import com.kemsky.kompas.data.repository.GithubRepository
import com.kemsky.kompas.data.room.GithubDatabase
import kotlinx.coroutines.flow.first
import timber.log.Timber

class AllUserDataSource(
    private val repository: GithubRepository,
    private val database: GithubDatabase
) : PagingSource<Int, UserModel>() {
    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        val dao = database.githubDao()
        try {
            val currentLoadingPageKey = params.key ?: 0
            val response = repository.getListUser(since = currentLoadingPageKey)
            val responseData = mutableListOf<UserModel>()
            if (response.code() == 200) {
                response.body()?.let { model ->
                    if (database.openHelper.writableDatabase.isOpen && model.isNotEmpty()) {
                        model.forEach {
                            dao.insertAllUser(it)
                        }
                    }
                }
            }
            dao.getAllUser().first { local ->
                Timber.e("local db ${local.size}")
                responseData.addAll(local)
//                responseData.addAll(local.filter {
//                    responseData.contains(it)
//                } )

//                local.takeIf {
//                    responseData.containsAll(it)
//                }?.toMutableList()?.let { responseData.addAll(it) }


//                if(responseData.containsAll(local)) {
//                    responseData.distinct().none()
//                } else {
//                    responseData.addAll(local)
//                }
            }

            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = responseData.last().id?.let { currentLoadingPageKey.plus(it) }
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}