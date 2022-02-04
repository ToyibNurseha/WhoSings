package com.toyibnurseha.whosings.db.converter

import androidx.room.TypeConverter
import com.toyibnurseha.whosings.db.model.ScoreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ScoreConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): MutableList<ScoreEntity> {
        val listType = object : TypeToken<MutableList<ScoreEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: MutableList<ScoreEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}