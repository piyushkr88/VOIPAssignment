package com.piyush.voipsimulation.ui.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.databinding.FragmentIncomingCallBinding
import com.piyush.voipsimulation.util.RingtonePlayer


class IncomingCallFragment : Fragment() {

    // Declare the ViewBinding object
    private var _binding: FragmentIncomingCallBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = FragmentIncomingCallBinding.inflate(inflater, container, false)

        // Return the root view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up listeners for Answer/Reject buttons
        binding.btnAnswer.setOnClickListener {
            RingtonePlayer.stop()
            findNavController().navigate(R.id.action_incoming_to_ongoing)
        }

        binding.btnReject.setOnClickListener {
            RingtonePlayer.stop()
            findNavController().navigate(R.id.action_ongoing_to_callLog)
        }

        // You can access the caller's name or number using:
         binding.tvCallerName.text = "Piyush Kumar"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nullify the binding when the view is destroyed to prevent memory leaks
        _binding = null
    }
}
