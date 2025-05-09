package com.piyush.voipsimulation.ui.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyush.voipsimulation.databinding.FragmentCallLogBinding
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallLogFragment : Fragment() {

    private lateinit var binding: FragmentCallLogBinding
    private val callLogViewModel: CallLogViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe call logs
        callLogViewModel.callLogs.observe(viewLifecycleOwner) { callLogs ->
            // Update UI with call logs
            // You can update your RecyclerView adapter with new data
        }

        // Observe missed calls
        callLogViewModel.missedCalls.observe(viewLifecycleOwner) { missedCalls ->
            // Update UI with missed calls
        }

        // Observe current call
        callLogViewModel.currentCall.observe(viewLifecycleOwner) { currentCall ->
            // Update UI based on current call state (answer, reject, etc.)
        }
    }
}

