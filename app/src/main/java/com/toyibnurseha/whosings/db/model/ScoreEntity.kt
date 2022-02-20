package com.toyibnurseha.whosings.db.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Entity(tableName = "score")
@Parcelize
data class ScoreEntity(

    @PrimaryKey
    @NonNull
    var date: Date = Date(),

    @NonNull
    var score: Int = 0

) : Parcelable
