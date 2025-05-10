package com.piyush.voipsimulation.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piyush.voipsimulation.data.dao.CallLogDao
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.data.model.Converters


@Database(entities = [CallLogEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CallLogDatabase : RoomDatabase() {
    abstract fun callLogDao(): CallLogDao

    companion object {
        @Volatile
        private var INSTANCE: CallLogDatabase? = null

        fun getInstance(context: Context): CallLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CallLogDatabase::class.java,
                    "call_log_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

