package com.slyfox.recall.manager.loading;

import android.support.v7.app.AppCompatActivity;

import com.slyfox.recall.domain.IContactLoader;

/**
 * Created by Eugene on 03.07.2016.
 *
 * This class manages all loader stuff to load and transform the contacts data in background thread.
 */
public class ContactLoadingManager implements IContactLoader {

    private AppCompatActivity activity;

    public ContactLoadingManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void loadContactNumbers(long contactId, NumbersCallback callback) {
        ContactNumbersTask numbersTask = new ContactNumbersTask(activity, contactId, callback);
        numbersTask.execute();
    }

    @Override
    public void loadContacts(ContactsCallback callback) {
        AllContactsCallback contactsCallback = new AllContactsCallback(activity, callback);
        activity.getSupportLoaderManager().initLoader(AllContactsCallback.LOADER_ID, null, contactsCallback);
    }
}
