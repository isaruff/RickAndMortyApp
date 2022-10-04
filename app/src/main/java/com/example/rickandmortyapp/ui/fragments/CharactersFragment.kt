package com.example.rickandmortyapp.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.adapters.CharactersLoadStateAdapter
import com.example.rickandmortyapp.adapters.CharactersPagingAdapter
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import com.example.rickandmortyapp.model.character_info.CharacterData
import com.example.rickandmortyapp.utils.isOnline
import com.example.rickandmortyapp.viewmodels.CharactersViewModel


class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterAdapter: CharactersPagingAdapter
    private val characterViewModel: CharactersViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCharacterPaging()
        refreshPaging()




        postponeEnterTransition()
        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        val characterDataListener = CharactersPagingAdapter.OnClickListener{characterData, characterImageView ->
            val directions: NavDirections =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(characterData)
            val extras = FragmentNavigatorExtras(
                characterImageView to characterData.image!!
            )
            findNavController().navigate(directions,extras)


        }
        binding.recyclerView.apply {
            characterAdapter = CharactersPagingAdapter(characterDataListener)
            layoutManager = GridLayoutManager(activity,2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return if (characterAdapter.itemCount == position) 2 else 1
                    }
                }
            }
            adapter = characterAdapter.withLoadStateFooter(
                footer = CharactersLoadStateAdapter{ characterAdapter.retry()}
            )
        }
    }

    private fun setupCharacterPaging() {
        if(isOnline(requireContext())){
            characterViewModel.characterPageData.observe(viewLifecycleOwner) {
                characterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                binding.connectionErrorView.visibility  = View.GONE
            }
        }else{
            binding.connectionErrorView.visibility = View.VISIBLE
        }

    }

    private fun refreshPaging(){
        binding.swipeToRefresh.setOnRefreshListener {
            setupCharacterPaging()
            binding.swipeToRefresh.isRefreshing = false
        }

    }




}