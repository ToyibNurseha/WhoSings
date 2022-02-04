package com.toyibnurseha.whosings.models

import com.google.gson.annotations.SerializedName

data class BodyData(
    @SerializedName("artist_list")
    var artistList: List<ArtistDataPair>?,

    @SerializedName("track_list")
    var trackList: List<TrackDataPair>?,

    @SerializedName("snippet")
    var snippetData: SnippetData?
)

