package com.toyibnurseha.whosings.repository

import android.util.Log
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.db.WhoSingsDAO
import com.toyibnurseha.whosings.local.Artist
import com.toyibnurseha.whosings.local.Snippet
import com.toyibnurseha.whosings.utils.ApiMapper
import com.toyibnurseha.whosings.utils.Constant.Companion.API_KEY
import com.toyibnurseha.whosings.utils.Resource
import com.toyibnurseha.whosings.utils.toArtistList
import com.toyibnurseha.whosings.utils.toSnippetLyrics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WhoSingRepository @Inject constructor(private val api: ApiMapper, private val dao: WhoSingsDAO) {

    val TAG = "repo"

    suspend fun getChartArtists(page: Int) = flow {
        var results : Resource<List<Artist>> = Resource.Loading()
        emit(results)

        kotlin.runCatching {
            api.getChartTracks("top", page, 3, "id", 1, API_KEY)
        }.onFailure {
            Log.e("GetMusicCharts", "getChartArtists error", )
            results = Resource.Error("an error occured")
        }.onSuccess {
            results = if (it.data != null) {
                Resource.Success(it.data.toArtistList())
            }else {
                Resource.Error("An exception occurred while retrieving chart artists")
            }
        }

        emit(results)

    }.flowOn(Dispatchers.IO)

    suspend fun getSnippetLyrics(trackId: Long) = flow {

        var result: Resource<Snippet> = Resource.Loading()
        emit(result)

        kotlin.runCatching {

            api.getSnippetLyrics(trackId, API_KEY)

        }.onFailure {

            result = Resource.Error("An exception occurred while retrieving snippet lyrics")

        }.onSuccess {

            result = if (it.data != null) {

                val snippet = it.data.toSnippetLyrics(trackId)

                if (snippet.text.isEmpty()) {
                    Resource.Error("An exception occurred while retrieving snippet lyrics")
                } else {
                    Resource.Success(snippet)
                }

            } else {
                Resource.Error("An exception occurred while retrieving snippet lyrics")
            }
        }

        emit(result)

    }.flowOn(Dispatchers.IO)

    fun updateUser(userEntity: UserEntity) = runBlocking {
        kotlin.runCatching {
            dao.updateUser(userEntity)
        }.onFailure {
        }.onSuccess {
        }
        return@runBlocking
    }
}