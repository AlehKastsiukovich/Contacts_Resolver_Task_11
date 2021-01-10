package by.itacademy.training.contactsresolver.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.itacademy.training.contactsresolver.databinding.ContactItemBinding
import by.itacademy.training.contactsresolver.model.data.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private lateinit var binding: ContactItemBinding
    private var contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun setData(contacts: List<Contact>) {
        this.contacts = contacts as MutableList<Contact>
        notifyDataSetChanged()
    }

    class ContactsViewHolder(viewItem: View, binding: ContactItemBinding) : RecyclerView.ViewHolder(viewItem) {
        private val contactImage = binding.contactTypeImageView
        private val contactName = binding.contactNameTextView
        private val contactValue = binding.contactTextView

        fun bind(contact: Contact) {
            contactImage.setImageResource(contact.imageResource)
            contactName.text = contact.contactName
            contactValue.text = contact.contact
        }
    }
}
