package com.toyibnurseha.whosings.ui.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyibnurseha.whosings.databinding.ItemRecordBinding
import com.toyibnurseha.whosings.db.model.ScoreEntity
import com.toyibnurseha.whosings.utils.dateToString

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    private var latestScores = ArrayList<ScoreEntity>()

    fun setData(items: MutableList<ScoreEntity>) {
        latestScores.clear()
        latestScores.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.ViewHolder {
        val view = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordAdapter.ViewHolder, position: Int) {
        holder.bind(latestScores[position])
    }

    override fun getItemCount(): Int = latestScores.size

    inner class ViewHolder(private val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(score: ScoreEntity) {
            binding.apply {
                tvDate.text = score.date.dateToString("hh:mm a E, dd MMM")
                tvScore.text = score.score.toString()
            }
        }
    }
}