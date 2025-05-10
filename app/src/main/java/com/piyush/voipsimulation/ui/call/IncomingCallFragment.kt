package com.piyush.voipsimulation.ui.call
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.databinding.FragmentIncomingCallBinding
import com.piyush.voipsimulation.receiver.CallBroadcastReceiver
import com.piyush.voipsimulation.util.NotificationHelper
import com.piyush.voipsimulation.util.RingtonePlayer
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncomingCallFragment : Fragment() {

    private var _binding: FragmentIncomingCallBinding? = null
    private val binding get() = _binding!!

    private val callLogViewModel: CallLogViewModel by viewModel()

    private var autoRejectJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomingCallBinding.inflate(inflater, container, false)

        startAutoRejectTimer()

        binding.btnAnswer.setOnClickListener {
            autoRejectJob?.cancel()
            handleAnsweredCall()
        }

        binding.btnReject.setOnClickListener {
            autoRejectJob?.cancel()
            handleMissedCall()
        }

        return binding.root
    }

    private fun startAutoRejectTimer() {
        autoRejectJob = lifecycleScope.launch {
            delay(10_000)
            handleMissedCall()
        }
    }

    private fun handleAnsweredCall() {
        cancelScheduledCall()
        RingtonePlayer.stop()
        navigateToOngoingCall()
    }

    private fun handleMissedCall() {
        val missedCall = CallLogEntity(
            callerName = "Piyush Kumar",
            callStartTime = System.currentTimeMillis(),
            callEndTime = System.currentTimeMillis(),
            duration = 0,
            callType = "Missed"
        )
        callLogViewModel.insertCallLog(missedCall)
        RingtonePlayer.stop()
        NotificationHelper.showMissedCallNotification(
            requireContext(),
            "John Doe",
            System.currentTimeMillis()
        )
        navigateToCallLogs()
    }

    private fun navigateToOngoingCall() {
        findNavController().navigate(R.id.ongoingCallFragment)
    }

    private fun navigateToCallLogs() {
        findNavController().navigate(R.id.callLogFragment)
    }

    override fun onDestroyView() {
        autoRejectJob?.cancel()
        _binding = null
        super.onDestroyView()
    }

    private fun cancelScheduledCall() {
        val intent = Intent(requireContext(), CallBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
