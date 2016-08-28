package com.slyfox.recall.domain;

import com.slyfox.recall.model.Contact;

import java.util.List;

/**
 * Created by Eugene on 23.07.2016.
 */
public interface IContactLoader {

    interface ContactsCallback {
        void onContactsLoaded(List<? extends Contact> contacts);
    }

    interface NumbersCallback {
        void onContactNumbersLoaded(List<String> numbers);
    }

    void loadContactNumbers(long contactId, NumbersCallback callback);

    void loadContacts(ContactsCallback callback);
}
