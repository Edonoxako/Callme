package com.slyfox.recall.domain;

import com.slyfox.recall.model.Contact;

import java.util.Collection;

/**
 * Created by Eugene on 23.07.2016.
 */
public interface IContactView {
    void showContacts(Collection<? extends Contact> contacts);
}
