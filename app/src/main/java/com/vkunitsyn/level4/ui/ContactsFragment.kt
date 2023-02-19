package com.vkunitsyn.level4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.adapter.ContactsAdapter
import com.vkunitsyn.level4.databinding.FragmentContactsBinding
import com.vkunitsyn.level4.model.Contact
import com.vkunitsyn.level4.utils.Constants
import com.vkunitsyn.level4.utils.FeatureFlags

class ContactsFragment : Fragment(), RecyclerViewInterface {


    private lateinit var viewModel: ContactsViewModel
    private lateinit var binding: FragmentContactsBinding
    private lateinit var adapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(View: View, savedInstanceState: Bundle?) {
        super.onViewCreated(View, savedInstanceState)
        postponeEnterTransition()
        setFragmentResultListener(Constants.ADD_CONTACT_FRAGMENT_RESULT) { _, bundle ->
            val contact = bundle.getParcelable<Contact>(Constants.ADDED_CONTACT)
            val position = adapter.itemCount
            if (contact != null) {
                addContact(position, contact)
            }
        }
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        initAdapter()
        viewModel.contactsList.observe(viewLifecycleOwner) {
            adapter.refresh(it)
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        adapter.onTrashBinClick = { position -> deleteContact(position) }
        processBackArrowClick()
        processAddContactClick()
        enableSwipeToDelete()
    }

    private fun enableSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                deleteContact(h.adapterPosition)
            }
        }).attachToRecyclerView(binding.rvContacts)
    }


    private fun processAddContactClick() {

        binding.tvAddContact.setOnClickListener {
            if (FeatureFlags.transactionsEnabled) {
                val dialogAddContact = AddContactFragment()
                dialogAddContact.show(parentFragmentManager, Constants.ADD_CONTACT_FRAGMENT_TAG)
            } else {
                findNavController().navigate(R.id.addContactFragment)
            }
        }
    }

    private fun processBackArrowClick() {
        binding.ibArrowBack.setOnClickListener {
            if (FeatureFlags.transactionsEnabled) {
                parentFragmentManager.popBackStack()
            } else {
                findNavController().popBackStack()
            }
        }
    }


    private fun initAdapter() {
        adapter = ContactsAdapter(this)
        binding.rvContacts.adapter = adapter
    }


    private fun deleteContact(position: Int) {
        val contact = viewModel.get(position)
        viewModel.removeAt(position)
        adapter.notifyItemRemoved(position)
        if (contact != null) {
            showSnackbar(contact, position)
        }
    }

    private fun addContact(position: Int, contact: Contact) {
        viewModel.add(position, contact)
        adapter.notifyItemInserted(position)
    }


    private fun showSnackbar(contact: Contact, position: Int) {
        Snackbar.make(
            binding.rvContacts,
            contact.name + getString(R.string.has_been_removed),
            Snackbar.LENGTH_SHORT
        ).setAction(R.string.undo) {
            addContact(position, contact)
        }.show()
    }

    override fun onItemClick(imageView: ImageView, position: Int) {
        val contact = viewModel.get(position)
        val bundle = Bundle()
        bundle.putParcelable("contact", contact)
        if (FeatureFlags.transactionsEnabled) {
            val contactsProfileFragment = ContactsProfileFragment()
            contactsProfileFragment.arguments = bundle
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out
                )
                setReorderingAllowed(true)
                addSharedElement(imageView, Constants.SHARED_ELEMENT_RECEIVER)
                replace(R.id.fragment_container, contactsProfileFragment)
                addToBackStack(null)
            }
        } else {
            val extras = FragmentNavigatorExtras(imageView to Constants.SHARED_ELEMENT_RECEIVER)
            val action =
                ContactsFragmentDirections.actionContactsFragmentToContactsProfileFragment(contact!!)
            findNavController().navigate(action, extras)
        }
    }
}