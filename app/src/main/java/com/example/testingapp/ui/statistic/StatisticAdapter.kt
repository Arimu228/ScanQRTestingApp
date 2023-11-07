package com.example.testingapp.ui.statistic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.data.QrCodeItem
import com.example.testingapp.data.QrCodeItemTest
import com.example.testingapp.data.QrCodeTime
import com.example.testingapp.databinding.ItemQrCodeRvBinding

class StatisticAdapter(val time: String) :
    RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder>() {

    private var qrCodeList = ArrayList<QrCodeTime>()

    inner class StatisticViewHolder(private var binding: ItemQrCodeRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(qrCodeTime: QrCodeTime) {
            binding.timeSet.text = qrCodeTime.time

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        return StatisticViewHolder(
            ItemQrCodeRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = qrCodeList.size

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        holder.bind(qrCodeList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setQrCode(list: List<QrCodeTime>) {
        qrCodeList.addAll(list)
        notifyDataSetChanged()
    }
}