package com.listen.rickandmortyyoutubeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.listen.rickandmortyyoutubeapp.R
import com.listen.rickandmortyyoutubeapp.databinding.CharacterLayoutBinding
import com.listen.rickandmortyyoutubeapp.models.Result

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    companion object {
        const val ALIVE = "Alive"
        const val UNKNOWN = "unknown"
        const val DEAD = "Dead"
    }

    class ViewHolder(private val binding: CharacterLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Result){
            binding.episodeAppearance.text = "Episode Appearance: ${character.episode.size}"
            binding.name.text = character.name
            binding.species.text = character.species
            binding.origin.text = character.origin.name
            Glide.with(itemView.context)
                .load(character.image)
                .centerCrop()
                .placeholder(androidx.constraintlayout.widget.R.color.material_grey_300)
                .into(binding.image)
            when(character.status){
                ALIVE -> {
                    binding.status.text = ALIVE
                    binding.status.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    binding.status.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.holo_green_light)
                    )
                }
                UNKNOWN -> {
                    binding.status.text = UNKNOWN
                    binding.status.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    binding.status.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.darker_gray)
                    )
                }
                DEAD -> {
                    binding.status.text = DEAD
                    binding.status.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    binding.status.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.holo_red_light)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CharacterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<com.listen.rickandmortyyoutubeapp.models.Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}