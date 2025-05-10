package com.piyush.voipsimulation.ui.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.piyush.voipsimulation.R
import com.piyush.voipsimulation.data.model.CallLogEntity
import com.piyush.voipsimulation.databinding.ItemCallLogBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CallLogAdapter : RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder>() {

    private val callLogs = mutableListOf<CallLogEntity>()

    fun submitList(logs: List<CallLogEntity>) {
        callLogs.clear()
        callLogs.addAll(logs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val binding = ItemCallLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CallLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        holder.bind(callLogs[position])
    }

    override fun getItemCount(): Int = callLogs.size

    inner class CallLogViewHolder(private val binding: ItemCallLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(callLog: CallLogEntity) {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

            binding.callerName.text = callLog.callerName
            binding.callTime.text = "Time: ${formatter.format(callLog.callStartTime)}"
            binding.callDuration.text = "Duration: ${callLog.duration} sec"
            if(callLog.callType.contains("Missed")){
                binding.callType.setImageResource(R.drawable.ic_missed)
            }else{
                binding.callType.setImageResource(R.drawable.ic_incoming)
            }
        }
    }
}
