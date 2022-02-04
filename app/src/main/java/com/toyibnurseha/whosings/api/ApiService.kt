package com.toyibnurseha.whosings.api

import com.toyibnurseha.whosings.models.ParentObjectData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("chart.tracks.get")
    suspend fun getMusicCharts(
        @Query("chart_name") chartName: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("country") country: String,
        @Query("f_has_lyrics") isHasLyrics: Boolean,
        @Query("apikey") apiKey: String,
    ): Response<ParentObjectData>

    @GET("track.snippet.get")
    suspend fun getSnippetLyrics(
        @Query("track_id") trackId: Long,
        @Query("apikey") apiKey: String
    ): Response<ParentObjectData>

}