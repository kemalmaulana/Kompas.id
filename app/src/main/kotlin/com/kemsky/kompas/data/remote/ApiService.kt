package com.kemsky.kompas.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.kemsky.kompas.constant.AppConstant.BASE_URL
import com.kemsky.kompas.data.model.DetailUserModel
import com.kemsky.kompas.data.model.GithubRepoModel
import com.kemsky.kompas.data.model.UserModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("/users")
    suspend fun getListUser(
        @Query("since") since: Int,
    ): Response<List<UserModel>>

    @GET("/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): Response<DetailUserModel>

    @GET("/users/{username}/repos")
    suspend fun getListRepos(
        @Path("username") username: String
    ): Response<List<GithubRepoModel>>


    companion object {
        private const val TIME_OUT = 60_000
        operator fun invoke(context: Context): ApiService {
            val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .collector(ChuckerCollector(context))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(true)
                        .build()
                )
                .addInterceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github+json")
                        .addHeader(
                            "Authorization",
                            "Bearer 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"
                        )
                        .build()
                    chain.proceed(request)
                }
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService::class.java)
        }
    }
}