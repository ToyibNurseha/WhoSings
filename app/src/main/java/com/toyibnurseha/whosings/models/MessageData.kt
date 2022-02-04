package com.toyibnurseha.whosings.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MessageData(
    @SerializedName("header")
    var header: HeaderData?,
    @SerializedName("body")
    var body: BodyData
)