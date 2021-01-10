package by.itacademy.training.contactsresolver.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import by.itacademy.training.contactsresolver.model.data.Contact
import by.itacademy.training.contactsresolver.resolver.SupportContentResolver
import by.itacademy.training.contactsresolver.util.Event
import by.itacademy.training.contactsresolver.util.Status
import java.lang.Exception

class ContactsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val supportContentResolver: SupportContentResolver = SupportContentResolver(application)
    val contacts: LiveData<Event<List<Contact>>>
        get() = _contacts
    private var _contacts = MutableLiveData<Event<List<Contact>>>()

    init {
        _contacts.value = Event(Status.SUCCESS, mutableListOf(), null)
        fetchData()
    }

    private fun fetchData() {
        _contacts = liveData {
            emit(Event(Status.LOADING, null, null))
            try {
                val result = supportContentResolver.getContactsFromOtherApp()
                emit(Event(Status.SUCCESS, result, null))
            } catch (e: Exception) {
                emit(Event(Status.ERROR, null, e.message))
            }
        } as MutableLiveData<Event<List<Contact>>>
    }
}
