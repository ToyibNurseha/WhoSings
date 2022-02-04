package com.toyibnurseha.whosings.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class HeaderData(
    @SerializedName("status_code")
    var statusCode: Int? = 0,
)