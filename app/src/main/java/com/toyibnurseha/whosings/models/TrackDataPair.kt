package com.toyibnurseha.whosings.models

import android.media.MediaParser
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TrackDataPair(
    @SerializedName("track")
    var track: TrackData?
)

