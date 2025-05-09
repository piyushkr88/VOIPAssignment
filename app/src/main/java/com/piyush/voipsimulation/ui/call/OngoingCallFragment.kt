package com.piyush.voipsimulation.ui.call

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.data.db.CallLogDatabase
import com.piyush.voipsimulation.data.local.CallLogDatabase
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.databinding.FragmentOngoingCallBinding
import com.piyush.voipsimulation.service.IncomingCallService

class OngoingCallFragment : Fragment(R.layout.fragment_ongoing_call) {

    private lateinit var binding: FragmentOngoingCallBinding
    private var callDuration: Long = 0L  // Explicitly define type as Int
    private val handler: Handler = Handler()  // Explicitly define type as Handler
    private val updateTimerRunnable: Runnable = Runnable {  // Explicitly define type as Runnable
        callDuration++
        binding.tvCallTimer.text = "Call Duration: $callDuration sec"
        handler.postDelayed(updateTimerRunnable, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOngoingCallBinding.inflate(inflater, container, false)

        handler.post(updateTimerRunnable)  // Start the timer

        binding.btnReject.setOnClickListener {
            handler.removeCallbacks(updateTimerRunnable)  // Stop the timer
            saveCallLog("Answered")
            activity?.supportFragmentManager?.popBackStack()  // Navigate back
        }

        startCallService()

        return binding.root
    }

    private fun startCallService() {
        val intent = Intent(requireContext(), IncomingCallService::class.java)
        requireContext().startService(intent)
    }

    private fun saveCallLog(callType: String) {
        val callLog = CallLogEntity(
            callerName = "Caller Name",
            callStartTime = System.currentTimeMillis() - (callDuration * 1000L),
            callEndTime = System.currentTimeMillis(),
            callDuration = callDuration,
            callType = callType
        )

        // Save to Room database
        val db = CallLogDatabase.getDatabase(requireContext())
        Thread {
            db.callLogDao().insert(callLog)
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateTimerRunnable)  // Clean up when fragment is destroyed
    }
}
