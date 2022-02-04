package com.toyibnurseha.whosings.db

import androidx.room.*
import com.toyibnurseha.whosings.db.model.UserEntity

@Dao
interface WhoSingsDAO {

    @Query("Select * from user where isLogged = 1")
    fun getLoggedUser(): UserEntity

    @Query("Select * from user where name = :name")
    fun getUser(name: String): UserEntity?

    @Query("Select * from user order by name asc")
    fun getUsers(): List<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)
}