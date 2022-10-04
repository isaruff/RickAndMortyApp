package com.example.rickandmortyapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentCustomDialogBinding
import com.example.rickandmortyapp.viewmodels.CharacterDetailsViewModel


class DetailsDialogFragment: DialogFragment(R.layout.fragment_custom_dialog) {

    private var _binding: FragmentCustomDialogBinding?=null
    private val binding get() = _binding!!
    private val args: DetailsDialogFragmentArgs by navArgs()
    private val detailsViewModel: CharacterDetailsViewModel by navGraphViewModels(R.id.home_navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillDetailsDialog()



    }


    private fun fillDetailsDialog() {
        if(args.condition){
            binding.apply {
                firstTitle.text = "Location Name:"
                detailsViewModel.locationLiveData.observe(viewLifecycleOwner){
                    firstTextView.text = it.name
                }

                secondTitle.text = "Location Type:"
                detailsViewModel.locationLiveData.observe(viewLifecycleOwner){
                    secondTextView.text = it.type
                }

                thirdTitle.text = "Dimension:"
                detailsViewModel.locationLiveData.observe(viewLifecycleOwner){
                    thirdTextView.text = it.dimension
                }
            }
        }
        else{
            binding.apply {
                firstTitle.text = "Episode Name:"
                detailsViewModel.episodeLiveData.observe(viewLifecycleOwner){
                    firstTextView.text = it.name
                }

                secondTitle.text = "Episode Code:"
                detailsViewModel.episodeLiveData.observe(viewLifecycleOwner){
                    secondTextView.text = it.episode
                }

                thirdTitle.text = "Air Date:"
                detailsViewModel.episodeLiveData.observe(viewLifecycleOwner){
                    thirdTextView.text = it.air_date
                }
            }
        }
    }
}