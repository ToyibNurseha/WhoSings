package com.toyibnurseha.whosings.ui.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyibnurseha.whosings.databinding.ItemScoreBinding
import com.toyibnurseha.whosings.local.Score

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    private var scores = ArrayList<Score>()

    fun setData(items: List<Score>) {
        scores.clear()
        scores.addAll(items)
        notifyDataSetChanged() //because need to change the whole item list not specific item
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardAdapter.ViewHolder {
        val view = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.ViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int = scores.size

    inner class ViewHolder(private val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score) {
            binding.tvRank.text = score.rank
            binding.tvUsername.text = score.username
            binding.tvScore.text = score.score
        }
    }
}