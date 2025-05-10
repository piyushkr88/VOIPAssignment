package com.piyush.voipsimulation.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.databinding.ActivityMainBinding
import com.piyush.voipsimulation.receiver.CallBroadcastReceiver
import com.piyush.voipsimulation.util.PermissionUtils
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val callLogViewModel: CallLogViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        callLogViewModel.currentCall.observe(this) { currentCall ->
            if (currentCall == null) {
                if (navController.currentDestination?.id != R.id.incomingCallFragment) {
                    navController.navigate(R.id.incomingCallFragment)
                }
            } else {
                if (navController.currentDestination?.id != R.id.ongoingCallFragment) {
                    navController.navigate(R.id.ongoingCallFragment)
                }
            }
        }

        if (intent.getBooleanExtra("IS_INCOMING_CALL", false)) {
            navController.navigate(R.id.incomingCallFragment)
        }
        val openCallLog = intent.getBooleanExtra("OPEN_CALL_LOG", false)
        if (openCallLog) {
            navController.navigate(R.id.callLogFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        if (PermissionUtils.isExactAlarmPermissionGranted(this)) {
            scheduleCall()
        } else {
            PermissionUtils.requestExactAlarmPermission(this)
        }
    }

    private fun scheduleCall() {
        val intent = Intent(this, CallBroadcastReceiver::class.java).apply {
            putExtra("CALL_TYPE", "INCOMING")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = System.currentTimeMillis() + 15 * 1000

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}
