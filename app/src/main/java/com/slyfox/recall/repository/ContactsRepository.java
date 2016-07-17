package com.slyfox.recall.repository;

import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 09.07.2016.
 */
public class ContactsRepository {

    private List<ContactModel> contacts;

    public ContactsRepository(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public ContactModel findContactById(long id) {
        for (ContactModel contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }
}
