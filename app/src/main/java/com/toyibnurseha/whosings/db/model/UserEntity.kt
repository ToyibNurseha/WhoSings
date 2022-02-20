package com.toyibnurseha.whosings.db.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toyibnurseha.whosings.db.model.ScoreEntity
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "user")
@Parcelize
data class UserEntity(

    @PrimaryKey
    @NonNull
    var name: String = "",

    @NonNull
    var isLogged: Boolean = false,

    @NonNull
    var scores: MutableList<ScoreEntity> = mutableListOf()

) : Parcelable
