package com.toyibnurseha.whosings.models

import com.google.gson.annotations.SerializedName

data class SnippetData(
    @SerializedName("snippet_id")
    var id: Long? = 0,

    @SerializedName("snippet_language")
    var language: String?,

    @SerializedName("snippet_body")
    var text: String?
)