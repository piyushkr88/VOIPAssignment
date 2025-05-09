package com.piyush.voipsimulation.ui.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyush.voipsimulation.data.local.CallLogEntity
import com.piyush.voipsimulation.databinding.ItemCallLogBinding

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
            binding.callerName.text = callLog.callerName
            binding.callTime.text = "Time: ${callLog.startTime}"
            binding.callDuration.text = "Duration: ${callLog.duration} sec"
//            binding.callType.text = "Type: ${callLog.callType}"
        }
    }
}
