package com.slyfox.recall.manager;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eugene on 03.07.2016.
 *
 * This class manages all loader stuff to load and transform the contacts data in background thread.
 */
public class ContactLoadingManager {

    private AppCompatActivity activity;

    public ContactLoadingManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    //Kicks-off the process of contact data loading
    public void loadContacts(AllContactsCallback.OnContactsLoadedCallback callback) {
        AllContactsCallback contactsCallback = new AllContactsCallback(activity, callback);
        activity.getSupportLoaderManager().initLoader(AllContactsCallback.LOADER_ID, null, contactsCallback);
    }

    public void loadSingleContact(long contactId, SingleContactsCallback.OnSingleContactLoadedCallback singleContactCallback) {
        SingleContactsCallback contactsCallback = new SingleContactsCallback(activity, contactId, singleContactCallback);
        activity.getSupportLoaderManager().restartLoader(SingleContactsCallback.LOADER_ID, null, contactsCallback);
    }
}
