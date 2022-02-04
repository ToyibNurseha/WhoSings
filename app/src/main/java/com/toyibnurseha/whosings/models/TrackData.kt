package com.toyibnurseha.whosings.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TrackData(
    @SerializedName("track_id")
    var id: Long? = 0,

    @SerializedName("track_name")
    var trackName: String? = "",

    @SerializedName("artist_name")
    var artistName: String? = "",
)