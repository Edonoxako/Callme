package com.slyfox.recall.manager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.getbase.android.db.loaders.CursorLoaderBuilder;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

import static com.slyfox.recall.manager.ContactDataTransformers.*;

/**
 * Created by Eugene on 03.07.2016.
 *
 * This class manages all loader stuff to load and transform the contacts data in background thread.
 */
public class ContactLoadingManager implements LoaderManager.LoaderCallbacks<List<ContactModel>> {

    public static final int CONTACT_LOADER_ID = 0; //loader id

    //This callback is used, when we need to return loading result
    public interface OnContactsLoadedCallback {
        void onContactsLoaded(List<ContactModel> contacts);
    }

    private OnContactsLoadedCallback callback;
    private AppCompatActivity activity;

    public ContactLoadingManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    //Kicks-off the process of contact data loading
    public void loadContacts(OnContactsLoadedCallback callback) {
        this.callback = callback; //keeps callback to invoke it when data loading is finished
        activity.getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<ContactModel>> onCreateLoader(int id, Bundle args) {
        if (id != CONTACT_LOADER_ID) return null; //do nothing if it is not the needed loader
        return CursorLoaderBuilder.forUri(ContactsContract.Contacts.CONTENT_URI)
                .projection(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                .where(ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?", 1) //we only need contacts that have phone numbers
                .transformRow(TO_CONTACT_MODEL) //transform from cursor to list
                .lazy() //there could be a lot of contacts, so we should load it only if we need it
                .build(activity);
    }

    @Override
    public void onLoadFinished(Loader<List<ContactModel>> loader, List<ContactModel> data) {
        //There MUST be callback to return loading result
        if (callback != null) callback.onContactsLoaded(data);
        else throw new IllegalStateException("No callback found!");
    }

    @Override
    public void onLoaderReset(Loader<List<ContactModel>> loader) {

    }
}
