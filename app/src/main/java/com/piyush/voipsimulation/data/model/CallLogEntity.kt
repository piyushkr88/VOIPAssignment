package com.piyush.voipsimulation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_log")
data class CallLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val callerName: String,
    val callType: String,
    val callStartTime: Long,
    val callEndTime: Long,
    val duration: Long
)
