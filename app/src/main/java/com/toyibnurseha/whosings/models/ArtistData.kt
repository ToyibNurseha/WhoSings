package com.toyibnurseha.whosings.models

import com.google.gson.annotations.SerializedName

data class ArtistDataPair(
    @SerializedName("artist")
    var artist: ArtistData?
)

data class ArtistData(
    @SerializedName("artist_id")
    var id: Long?,

    @SerializedName("artist_mbid")
    var mBId: String?,

    @SerializedName("artist_name")
    var name: String?,

    @SerializedName("artist_rating")
    var rating: Int?,
)