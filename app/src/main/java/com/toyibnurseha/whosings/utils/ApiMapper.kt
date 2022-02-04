package com.toyibnurseha.whosings.utils

import com.toyibnurseha.whosings.api.ApiService
import javax.inject.Inject

class ApiMapper @Inject constructor(private val apiService: ApiService) : ApiHelper() {

    suspend fun getChartTracks(
        chartName: String,
        page: Int,
        size: Int,
        country: String,
        hasLyrics: Int,
        apikey: String
    ) = getResult {
        apiService.getMusicCharts(chartName, page, size, country, true, apikey)
    }

    suspend fun getSnippetLyrics(trackId: Long, apikey: String) = getResult {
        apiService.getSnippetLyrics(trackId, apikey)
    }

}