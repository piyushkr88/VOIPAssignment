package com.piyush.voipsimulation.data.repository

import com.piyush.voipsimulation.data.dao.CallLogDao
import com.piyush.voipsimulation.data.model.CallLogEntity


class CallLogRepository(private val dao: CallLogDao) {

    // Insert a new call log
    suspend fun insertLog(log: CallLogEntity) = dao.insert(log)

    // Get all call logs as Flow to allow reactive updates in ViewModel
    fun getAllLogs() = dao.getAllCallLogs()

    // Get missed calls as Flow
    fun getMissedLogs() = dao.getMissedCalls()

    // Clear all logs
    suspend fun clearLogs() = dao.clearLogs()
}



