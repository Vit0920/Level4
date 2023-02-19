package com.vkunitsyn.level4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.vkunitsyn.level4.databinding.FragmentContactsProfileBinding
import com.vkunitsyn.level4.model.Contact
import com.vkunitsyn.level4.utils.FeatureFlags
import com.vkunitsyn.level4.utils.addPictureGlide

class ContactsProfileFragment : Fragment() {
    private lateinit var binding: FragmentContactsProfileBinding
    private val args: ContactsProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsProfileBinding.inflate(inflater)
        val transition = context?.let {
            TransitionInflater.from(it).inflateTransition(android.R.transition.move)
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = if (FeatureFlags.transactionsEnabled) {
            arguments?.getParcelable<Contact>("contact")
        } else {
            args.contact
        }
        binding.apply {
            imgProfilePicture.addPictureGlide(contact?.picture)
            tvName.text = contact?.name
            tvCareer.text = contact?.career
            tvAddress.text = contact?.address
        }
        processBackArrowClick()
    }

    private fun processBackArrowClick() {
        binding.ibArrowBackContactProfile.setOnClickListener {
            if (FeatureFlags.transactionsEnabled) {
                parentFragmentManager.popBackStack()
            } else {
                findNavController().popBackStack()
            }
        }
    }
}
