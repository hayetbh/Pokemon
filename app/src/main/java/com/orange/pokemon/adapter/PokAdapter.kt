package com.orange.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.orange.pokemon.Data.PokEntity
import com.orange.pokemon.R
import com.orange.pokemon.databinding.ItemPokemonBinding

class PokAdapter : ListAdapter<PokEntity, PokAdapter.PokemonViewHolder>(PokemonDiffUtils()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokAdapter.PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PokemonViewHolder(val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemonEntity: PokEntity) {
            val context = itemView.context
            with(binding) {
                pokNom.text = pokemonEntity.name
                pokDescription.text = pokemonEntity.xdescription
                Glide.with(context)
                    .load(pokemonEntity.imageurl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.pokImg)

            }
        }

    }

}

class PokemonDiffUtils : DiffUtil.ItemCallback<PokEntity>() {
    override fun areItemsTheSame(oldItem: PokEntity, newItem: PokEntity): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: PokEntity, newItem: PokEntity): Boolean {
        TODO("Not yet implemented")
    }

}