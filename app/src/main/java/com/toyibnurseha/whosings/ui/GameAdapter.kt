package com.toyibnurseha.whosings.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyibnurseha.whosings.databinding.ItemArtistBinding
import com.toyibnurseha.whosings.local.Artist

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var artistList = ArrayList<Artist>()

    fun setData(items: List<Artist>) {
        artistList.clear()
        artistList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val view = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

    override fun getItemCount(): Int = artistList.size

    inner class GameViewHolder(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.tvArtist.text = artist.name
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(artist)
                }
            }
        }
    }

    private var onItemClickListener: ((Artist) -> Unit) ? = null

    fun setOnItemClickListener(listener: (Artist) -> Unit) {
        onItemClickListener = listener
    }

}