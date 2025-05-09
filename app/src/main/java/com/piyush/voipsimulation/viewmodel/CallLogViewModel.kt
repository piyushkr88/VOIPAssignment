package com.piyush.voipsimulation.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.data.repository.CallLogRepository
import kotlinx.coroutines.launch
class CallLogViewModel(private val repository: CallLogRepository) : ViewModel() {

    private val _callLogs = MutableLiveData<List<CallLogEntity>>()
    val callLogs: LiveData<List<CallLogEntity>> get() = _callLogs

    private val _missedCalls = MutableLiveData<List<CallLogEntity>>()
    val missedCalls: LiveData<List<CallLogEntity>> get() = _missedCalls

    private val _currentCall = MutableLiveData<CallLogEntity>()
    val currentCall: LiveData<CallLogEntity> get() = _currentCall

    // Collect call logs when ViewModel is initialized
    init {
        fetchAllCallLogs()
        fetchMissedCalls()
    }

    // Collect all call logs
    fun fetchAllCallLogs() {
        viewModelScope.launch {
            repository.getAllLogs().collect { logs ->
                _callLogs.value = logs
            }
        }
    }

    // Collect missed calls
    fun fetchMissedCalls() {
        viewModelScope.launch {
            repository.getMissedLogs().collect { missedLogs ->
                _missedCalls.value = missedLogs
            }
        }
    }

    // Insert a new call log
    fun insertCallLog(callLog: CallLogEntity) {
        viewModelScope.launch {
            repository.insertLog(callLog)
        }
    }

    // Set the current call
    fun setCurrentCall(callLog: CallLogEntity) {
        _currentCall.value = callLog
    }
}
