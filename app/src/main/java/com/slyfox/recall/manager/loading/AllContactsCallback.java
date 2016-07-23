package com.slyfox.recall.manager.loading;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.getbase.android.db.loaders.CursorLoaderBuilder;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 17.07.2016.
 */
public class AllContactsCallback implements LoaderManager.LoaderCallbacks<List<ContactModel>> {

    public static final int LOADER_ID = 0; //loader id

    //This contactsCallback is used, when we need to return loading result
    public interface OnContactsLoadedCallback {
        void onContactsLoaded(List<ContactModel> contacts);
    }

    private AppCompatActivity activity;
    private OnContactsLoadedCallback contactsCallback;

    public AllContactsCallback(AppCompatActivity activity, OnContactsLoadedCallback contactsCallback) {
        this.activity = activity;
        this.contactsCallback = contactsCallback; //keeps contactsCallback to invoke it when data loading is finished
    }

    @Override
    public Loader<List<ContactModel>> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return CursorLoaderBuilder.forUri(ContactsContract.Contacts.CONTENT_URI)
                    .projection(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    .where(ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?", 1) //we only need contacts that have phone numbers
                    .transformRow(ContactDataTransformers.ROW_TO_CONTACT_MODEL) //transform from cursor to list
                    .lazy() //there could be a lot of contacts, so we should load it only if we need it
                    .build(activity);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<ContactModel>> loader, List<ContactModel> data) {
        //There MUST be contactsCallback to return loading result
        if (contactsCallback != null) contactsCallback.onContactsLoaded(data);
        else throw new IllegalStateException("No contactsCallback found!");
    }

    @Override
    public void onLoaderReset(Loader<List<ContactModel>> loader) {

    }
}
