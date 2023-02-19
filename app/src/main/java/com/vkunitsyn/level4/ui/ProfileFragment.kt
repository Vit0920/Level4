package com.vkunitsyn.level4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.databinding.FragmentProfileBinding
import com.vkunitsyn.level4.utils.Constants
import com.vkunitsyn.level4.utils.FeatureFlags


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.text = if (FeatureFlags.transactionsEnabled) {
            arguments?.getString(Constants.USER_NAME)
        } else {
            args.userName
        }

        processViewContactsButtonClick()
        processEditProfileButtonClick()
    }

    private fun processEditProfileButtonClick() {
        binding.editProfile.setOnClickListener() {

            if (FeatureFlags.transactionsEnabled) {
                parentFragmentManager.commit {
                    setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    replace<EditProfileFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
            } else {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }

        }
    }

    private fun processViewContactsButtonClick() {
        binding.mbViewContacts.setOnClickListener() {
            if (FeatureFlags.transactionsEnabled) {
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out
                    )
                    replace<ContactsFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
            } else {
                findNavController().navigate(R.id.action_profileFragment_to_contactsFragment)
            }
        }
    }
}