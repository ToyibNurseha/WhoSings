package com.toyibnurseha.whosings.models

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ParentObjectData(
    @SerializedName("message")
    var parentObjectData: MessageData?
)