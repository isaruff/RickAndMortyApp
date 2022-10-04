package com.example.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.PagingLoadStateFooterBinding

class CharactersLoadStateAdapter(private val retry: ()-> Unit): LoadStateAdapter<CharactersLoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(private val binding: PagingLoadStateFooterBinding)
        : RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener{
                retry.invoke()
            }
        }

            fun bind(loadState: LoadState){

                binding.apply {
                    progressBar.isVisible = loadState is LoadState.Loading
                    retryButton.isVisible = loadState !is LoadState.Loading
                    errorTextView.isVisible = loadState !is LoadState.Loading

                }
            }


    }
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PagingLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}