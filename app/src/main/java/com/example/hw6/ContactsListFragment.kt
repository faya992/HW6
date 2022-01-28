package com.example.hw6

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hw6.databinding.FragmentContactsListBinding


class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private val binding by viewBinding(FragmentContactsListBinding::bind)
    private val viewModel by viewModels<ContactsListViewModel>()
    private val adapter by lazy {
        ContactsAdapter().apply {
            listener = object : OnContactClickListener {
                override fun onClick(contact: Contact) {
                    viewModel.onContactClick(contact)
                }

                override fun onLongClick(contact: Contact, anchorView: View) {
                    showDeletePopupMenu(contact, anchorView)
                }
            }
        }
    }

    fun saveContact(oldContact: Contact, newContact: Contact) {
        viewModel.saveContact(oldContact, newContact)
        adapter.filterList(binding.contactSearchView.query.toString())
    }

    private fun showDeletePopupMenu(contact: Contact, anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.inflate(R.menu.contacts_list_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            viewModel.onContactLongClick(contact)
            true
        }
        popupMenu.show()
    }

    private lateinit var contactsListListener: ContactClickedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val launchActivity = requireActivity()
        if (launchActivity is ContactClickedListener) {
            contactsListListener = launchActivity
        } else {
            throw IllegalStateException("Launch activity must implement ContactsListListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        initViewModel()
        initSearchView()
        viewModel.onViewIsReady()
    }

    private fun initViewModel() {
        viewModel.setContactsLiveData.observe(viewLifecycleOwner) { contacts ->
            adapter.contactsList = contacts
            adapter.notifyDataSetChanged()
            adapter.filterList(binding.contactSearchView.query.toString())
        }
        viewModel.openContactDetailLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { contact ->
                contactsListListener.openContactDetails(contact)
            }
        }
        viewModel.dispatchDiffUtilLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.dispatchUpdatesTo(adapter)
        }
    }

    private fun initSearchView() {
        binding.contactSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filterList(newText)
                return true
            }
        })
    }
}