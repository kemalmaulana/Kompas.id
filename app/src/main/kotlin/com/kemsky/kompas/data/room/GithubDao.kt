package com.kemsky.kompas.data.room

import androidx.room.*
import com.kemsky.kompas.data.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    // User Model
    @Query("SELECT * FROM tbl_user_model")
    fun getAllUser(): Flow<List<UserModel>>

    @Query("SELECT * FROM tbl_user_model WHERE id == :id")
    fun getSingleUser(id: Int): Flow<UserModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(vararg users: UserModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleUser(users: UserModel)

    @Delete
    suspend fun deleteUser(user: UserModel)

//    // Detail User Model
//    @Query("SELECT * FROM tbl_detail_user_model")
//    fun getAllDetailUser(): Flow<List<DetailUserModel>>
//
//    @Query("SELECT * FROM tbl_detail_user_model WHERE id == :id")
//    fun getSingleDetailUser(id: Int): Flow<DetailUserModel?>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllDetailUser(vararg users: DetailUserModel)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSingleDetailUser(users: DetailUserModel)
//
//    @Delete
//    suspend fun deleteDetailUser(user: DetailUserModel)

//    // Repo Model
//    @Query("SELECT * FROM tbl_repo_model")
//    fun getAllRepo(): Flow<List<GithubRepoModel>>
//
//    @Query("SELECT * FROM tbl_repo_model WHERE id == :id")
//    fun getSingleRepo(id: Int): Flow<GithubRepoModel?>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllRepo(vararg users: GithubRepoModel)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSingleRepo(users: GithubRepoModel)
//
//    @Delete
//    suspend fun deleteRepo(user: GithubRepoModel)
}