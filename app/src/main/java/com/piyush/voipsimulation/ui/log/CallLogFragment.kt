package com.piyush.voipsimulation.ui.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyush.voipsimulation.databinding.FragmentCallLogBinding
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class CallLogFragment : Fragment() {

    private var _binding: FragmentCallLogBinding? = null
    private val binding get() = _binding!!

    private val callLogViewModel: CallLogViewModel by viewModel()
    private lateinit var callLogAdapter: CallLogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCallLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callLogAdapter = CallLogAdapter()
        binding.callLogRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.callLogRecyclerView.adapter = callLogAdapter

        callLogViewModel.fetchAllCallLogs()

        callLogViewModel.callLogs.observe(viewLifecycleOwner) { callLogs ->
            callLogAdapter.submitList(callLogs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
