package com.kemsky.kompas.data.repository

import com.kemsky.kompas.data.model.DetailUserModel
import com.kemsky.kompas.data.model.GithubRepoModel
import com.kemsky.kompas.data.model.UserModel
import retrofit2.Response

interface GithubRepository {

    suspend fun getListUser(since: Int): Response<List<UserModel>>
    suspend fun getListRepo(username: String): Response<List<GithubRepoModel>>
    suspend fun getDetailUser(username: String): Response<DetailUserModel>

}