package com.piyush.voipsimulation.ui.call

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.databinding.FragmentOngoingCallBinding
import com.piyush.voipsimulation.receiver.CallBroadcastReceiver
import com.piyush.voipsimulation.service.OngoingCallService
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OngoingCallFragment : Fragment() {

    private var _binding: FragmentOngoingCallBinding? = null
    private val binding get() = _binding!!

    private var callDuration = 0L
    private var timerJob: Job? = null

    private val callLogViewModel: CallLogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOngoingCallBinding.inflate(inflater, container, false)
        val intent = Intent(requireContext(), OngoingCallService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
        startCallTimer()

        binding.btnReject.setOnClickListener {
            stopCallTimer()
            val endedCall = CallLogEntity(
                callerName = "Piyush Kumar",
                callStartTime = System.currentTimeMillis() - (callDuration * 1000L),
                callEndTime = System.currentTimeMillis(),
                duration = callDuration,
                callType = "Answered"
            )
            callLogViewModel.insertCallLog(endedCall)
            requireContext().stopService(Intent(requireContext(), OngoingCallService::class.java))

            navigateToCallLogs()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()


    }

    private fun startCallTimer() {
        timerJob = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(1000)
                callDuration++
                binding.tvCallTimer.text = "Call Duration: $callDuration sec"
            }
        }
    }

    private fun stopCallTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun navigateToCallLogs() {
        findNavController().navigate(
            R.id.callLogFragment,
            null,
            navOptions {
                popUpTo(R.id.ongoingCallFragment) {
                    inclusive = true
                }
            }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopCallTimer()
        _binding = null
    }


}
