package com.piyush.voipsimulation.data.repository

import com.piyush.voipsimulation.data.dao.CallLogDao
import com.piyush.voipsimulation.data.model.CallLogEntity


class CallLogRepository(private val dao: CallLogDao) {

    suspend fun insertLog(log: CallLogEntity) = dao.insert(log)

    fun getAllLogs() = dao.getAllCallLogs()

    fun getMissedLogs() = dao.getMissedCalls()

}



