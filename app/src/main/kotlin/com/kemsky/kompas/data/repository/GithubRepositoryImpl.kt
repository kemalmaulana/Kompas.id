package com.kemsky.kompas.data.repository

import com.kemsky.kompas.data.model.DetailUserModel
import com.kemsky.kompas.data.model.GithubRepoModel
import com.kemsky.kompas.data.model.UserModel
import com.kemsky.kompas.data.remote.ApiService
import retrofit2.Response


class GithubRepositoryImpl(private val apiService: ApiService) : GithubRepository {

    override suspend fun getListUser(since: Int): Response<List<UserModel>> {
        return apiService.getListUser(since)
    }

    override suspend fun getListRepo(username: String): Response<List<GithubRepoModel>> {
        return apiService.getListRepos(username)
    }

    override suspend fun getDetailUser(username: String): Response<DetailUserModel> {
        return apiService.getDetailUser(username)
    }

}