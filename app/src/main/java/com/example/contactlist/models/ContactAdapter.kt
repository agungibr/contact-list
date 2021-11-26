package com.example.contactlist.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.databinding.ContactItemBinding

class ContactAdapter (var contacts: List<Contact>, var clicker: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContact(contact: Contact) {
            binding.apply {
                idDisplay.text = contact.id.toString()
                nameDisplay.text = contact.name
                numberDisplay.text = contact.number
                root.setOnClickListener { clicker(contact) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context))
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindContact(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}