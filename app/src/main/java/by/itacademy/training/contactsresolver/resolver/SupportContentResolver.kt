package by.itacademy.training.contactsresolver.resolver

import android.content.Context
import android.database.Cursor
import android.net.Uri
import by.itacademy.training.contactsresolver.model.data.Contact

class SupportContentResolver(context: Context) {

    private val contextResolver = context.contentResolver

    fun getContactsFromOtherApp(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val uri = Uri.parse(DATA_URL)
        val cursor = contextResolver.query(
            uri,
            arrayOf(CONTACT_NAME_DB, IMAGE_RESOURCE_DB, CONTACT_DB),
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val contact = createContact(cursor)
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }

    private fun createContact(cursor: Cursor): Contact {
        val contactIndex = cursor.getColumnIndex(CONTACT_DB)
        val contactNameIndex = cursor.getColumnIndex(CONTACT_NAME_DB)
        val imageResourceIndex = cursor.getColumnIndex(IMAGE_RESOURCE_DB)
        val contact = cursor.getString(contactIndex)
        val contactName = cursor.getString(contactNameIndex)
        val imageResource = cursor.getInt(imageResourceIndex)
        return Contact(contact = contact, contactName = contactName, imageResource = imageResource)
    }

    companion object {
        private const val TABLE_NAME = "contacts"
        private const val PROVIDER_NAME = "by.itacademy.training.task8.provider.ContactsProvider"
        private const val DATA_URL = "content://$PROVIDER_NAME/$TABLE_NAME"
        const val CONTACT_NAME_DB = "contactName"
        const val IMAGE_RESOURCE_DB = "imageResource"
        const val CONTACT_DB = "contact"
    }
}
