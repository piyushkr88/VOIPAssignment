package com.piyush.voipsimulation.data.dao

import androidx.room.*
import com.piyush.voipsimulation.data.model.CallLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {

    @Insert
    suspend fun insert(callLog: CallLogEntity)

    @Query("SELECT * FROM call_log ORDER BY callStartTime DESC")
    fun getAllCallLogs(): Flow<List<CallLogEntity>>

    @Query("SELECT * FROM call_log WHERE callType = 'Missed' ORDER BY callStartTime DESC")
    fun getMissedCalls(): Flow<List<CallLogEntity>>
}


