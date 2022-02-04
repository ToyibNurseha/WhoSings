package com.toyibnurseha.whosings.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.toyibnurseha.whosings.db.converter.DateConverter
import com.toyibnurseha.whosings.db.converter.ScoreConverter
import com.toyibnurseha.whosings.db.model.ScoreEntity
import com.toyibnurseha.whosings.db.model.UserEntity

@Database(
    exportSchema = false, version = 1, entities = [
        UserEntity::class,
        ScoreEntity::class
    ]
)
@TypeConverters(
    ScoreConverter::class,
    DateConverter::class
)
abstract class WhoSingsDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "who_sings.db"

        private var instance: WhoSingsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): WhoSingsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, WhoSingsDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun whoSingsDAO(): WhoSingsDAO

}