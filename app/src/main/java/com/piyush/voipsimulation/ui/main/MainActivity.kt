package com.piyush.voipsimulation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.databinding.ActivityMainBinding
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

        navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        observeViewModel()

        handleIncomingIntent()

        // Permission check moved to ViewModel or PermissionManager (Optional)
        if (PermissionUtils.isExactAlarmPermissionGranted(this)) {
            callLogViewModel.scheduleCall(this)
        } else {
            PermissionUtils.requestExactAlarmPermission(this)
        }
    }

    private fun observeViewModel() {
        callLogViewModel.currentCall.observe(this) { currentCall ->
            when {
                currentCall == null && navController.currentDestination?.id != R.id.incomingCallFragment -> {
                    navController.navigate(R.id.incomingCallFragment)
                }
                currentCall != null && navController.currentDestination?.id != R.id.ongoingCallFragment -> {
                    navController.navigate(R.id.ongoingCallFragment)
                }
            }
        }
    }

    private fun handleIncomingIntent() {
        if (intent.getBooleanExtra("IS_INCOMING_CALL", false)) {
            navController.navigate(R.id.incomingCallFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        callLogViewModel.handleAlarmPermissionOnResume(this)
    }
}
