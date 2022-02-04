package com.toyibnurseha.whosings.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*

@Entity(tableName = "score")
data class ScoreEntity(

    @PrimaryKey
    @NonNull
    var date: Date = Date(),

    @NonNull
    var score: Int = 0

) : Serializable
