package com.toyibnurseha.whosings.db.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toyibnurseha.whosings.db.model.ScoreEntity
import java.io.Serializable

@Entity(tableName = "user")
data class UserEntity(

    @PrimaryKey
    @NonNull
    var name: String = "",

    @NonNull
    var isLogged: Boolean = false,

    @NonNull
    var scores: MutableList<ScoreEntity> = mutableListOf()

) : Serializable
