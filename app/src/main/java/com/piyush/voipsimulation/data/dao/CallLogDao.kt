package com.piyush.voipsimulation.data.dao

import androidx.room.*
import com.piyush.voipsimulation.data.model.CallLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {

    // Insert a call log
    @Insert
    suspend fun insert(callLog: CallLogEntity)

    // Fetch all call logs with Flow for reactive updates
    @Query("SELECT * FROM call_log ORDER BY callStartTime DESC")
    fun getAllCallLogs(): Flow<List<CallLogEntity>>

    // Fetch missed calls with Flow for reactive updates
    @Query("SELECT * FROM call_log WHERE callType = 'Missed' ORDER BY callStartTime DESC")
    fun getMissedCalls(): Flow<List<CallLogEntity>>

    // Clear all call logs
    @Query("DELETE FROM call_log")
    suspend fun clearLogs()
}


