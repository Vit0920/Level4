package com.vkunitsyn.level4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vkunitsyn.level4.databinding.FragmentEditProfileBinding
import com.vkunitsyn.level4.utils.FeatureFlags

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        processSaveButtonClick()
        processBackButtonClick()
    }

    private fun processSaveButtonClick() {

    }

    private fun processBackButtonClick() {
        binding.ibArrowBackEditProfile.setOnClickListener() {
            if (FeatureFlags.transactionsEnabled) {
                parentFragmentManager.popBackStack()
            } else {
                findNavController().popBackStack()
            }
        }
    }
}