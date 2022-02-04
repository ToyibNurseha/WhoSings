package com.toyibnurseha.whosings.repository

import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.db.WhoSingsDAO
import com.toyibnurseha.whosings.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(private val dao: WhoSingsDAO) {


    suspend fun getLoggedUser() = flow {

        var result: Resource<UserEntity> = Resource.Loading()
        emit(result)

        kotlin.runCatching {
            dao.getLoggedUser()
        }.onFailure {
            result = Resource.Error("An exception occurred")
        }.onSuccess {
            result = Resource.Success(it)
        }

        emit(result)
    }.flowOn(Dispatchers.IO)

    suspend fun insertUser(user: UserEntity) = flow {

        kotlin.runCatching {
            val dbUser = dao.getUser(user.name)?.apply {
                isLogged = true
            } ?: user
            dao.insertUser(dbUser)
        }.onSuccess {
            emit(true)
        }.onFailure {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUsers() = flow {

        var result: Resource<List<UserEntity>> = Resource.Loading()

        kotlin.runCatching {
            dao.getUsers()
        }.onSuccess {
            result = Resource.Success(it ?: emptyList())
        }.onFailure {
            result = Resource.Success(emptyList())
        }

        emit(result)
    }.flowOn(Dispatchers.IO)
}