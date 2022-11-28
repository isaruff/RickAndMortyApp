package com.example.rickandmortyapp.ui.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import coil.load
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmortyapp.viewmodels.CharacterDetailsViewModel
import java.util.concurrent.TimeUnit


class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val detailsViewModel: CharacterDetailsViewModel by navGraphViewModels(R.id.home_navigation_graph)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)
        sharedElementReturnTransition = animation
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransition()
        setupViews()
        setupBackButton()
        openDialogFragment()
        setLiveData()






    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupViews() {
        binding.apply {
            characterImageView.load(args.characterData.image) {
                placeholder(R.drawable.preloading_animation)
            }
            characterNameTextView.text = args.characterData.name
            statusTextView.text = args.characterData.status
            lastLocationTextView.text = args.characterData.location?.name
            speciesTextView.text = args.characterData.species
            statusIcon(args.characterData.status!!)
            observeEpisodeData()
        }
    }

    private fun setTransition(){
        binding.characterImageView.transitionName = args.characterData.image
    }

    private fun statusIcon(status: String) {
        when (status) {
            "Alive" -> {
                binding.statusImageView.load(R.drawable.status_alive)
            }
            "Dead" -> {
                binding.statusImageView.load(R.drawable.status_dead)
            }
            else -> {
                binding.statusImageView.load(R.drawable.status_unknown)
            }
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLiveData(){
        detailsViewModel.getLocationLiveData(args.characterData.location!!.url!!)
        detailsViewModel.getEpisodeLiveData(args.characterData.episode[0]!!)
    }

    private fun observeEpisodeData() {
        binding.firstSeenEpisode.text = "Loading..."
        detailsViewModel.episodeLiveData.observe(viewLifecycleOwner) {
            binding.firstSeenEpisode.text = it.name

        }
    }

    private fun openDialogFragment() {
        binding.lastLocationTextView.setOnClickListener{
            findNavController().navigate(
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToDetailsDialogFragment(true)
            )
        }
        binding.firstSeenEpisode.setOnClickListener {
            findNavController().navigate(
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToDetailsDialogFragment(false))
        }
    }

}