package by.itacademy.training.contactsresolver.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.training.contactsresolver.databinding.ActivityMainBinding
import by.itacademy.training.contactsresolver.model.data.Contact
import by.itacademy.training.contactsresolver.ui.adapter.ContactsAdapter
import by.itacademy.training.contactsresolver.ui.viewmodel.ContactsViewModel
import by.itacademy.training.contactsresolver.util.Event
import by.itacademy.training.contactsresolver.util.Status
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var model: ContactsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        setAdaptersProperties()
        setNoContactMessageVisibility()
        renderData()
    }

    private fun setUpViewModel() {
        model = ViewModelProvider(this).get(ContactsViewModel::class.java)
    }

    private fun setAdaptersProperties() {
        contactsAdapter = ContactsAdapter()
        binding.recyclerView.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun renderData() {
        model.contacts.observe(this, { setUpVisibility(it) })
    }

    private fun setUpVisibility(event: Event<List<Contact>>) {
        when (event.status) {
            Status.LOADING -> viewProgressBar()
            Status.SUCCESS -> {
                hideProgressBar()
                event.data?.let { list -> contactsAdapter.setData(list) }
            }
            Status.ERROR -> inform(event.message)
        }
    }

    private fun viewProgressBar() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        with(binding) {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setNoContactMessageVisibility() {
        if (model.contacts.value == null) {
            binding.noContactsMessage.visibility = View.VISIBLE
        } else {
            binding.noContactsMessage.visibility = View.INVISIBLE
        }
    }

    private fun inform(message: String?) {
        Snackbar.make(binding.parentLayout, message as CharSequence, Snackbar.LENGTH_LONG).show()
    }
}
