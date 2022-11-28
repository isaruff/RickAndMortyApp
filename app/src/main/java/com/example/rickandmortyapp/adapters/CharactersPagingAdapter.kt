package com.example.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.CharacterItemBinding
import com.example.rickandmortyapp.model.character_info.CharacterData
import com.example.rickandmortyapp.ui.fragments.CharactersFragmentDirections

class CharactersPagingAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<CharacterData, CharactersPagingAdapter.CharactersViewHolder>(DiffCallBack()) {

    class CharactersViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val currentData = getItem(position)
        if (currentData != null) {
            holder.binding.apply {
                characterNameTextView.text = currentData.name
                characterImageView.load(currentData.image) {
                    placeholder(R.drawable.preloading_animation)
                }
                characterImageView.transitionName = currentData.image
                root.setOnClickListener {
                    onClickListener.onClick(currentData,characterImageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class DiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name && oldItem.image == newItem.image
        }
    }

    class OnClickListener(val clickListener: (CharacterData, ImageView) -> Unit) {
        fun onClick(
            data: CharacterData,
            characterImageView: ImageView,
        ) = clickListener(data, characterImageView)
    }
}




