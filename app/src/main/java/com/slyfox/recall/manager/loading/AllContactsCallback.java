package com.slyfox.recall.manager.loading;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.getbase.android.db.loaders.CursorLoaderBuilder;
import com.slyfox.recall.domain.IContactLoader;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 17.07.2016.
 */
public class AllContactsCallback implements LoaderManager.LoaderCallbacks<List<ContactModel>> {

    public static final int LOADER_ID = 0; //loader id

    private AppCompatActivity activity;
    private IContactLoader.ContactsCallback loaderContactcCallback;

    public AllContactsCallback(AppCompatActivity activity, IContactLoader.ContactsCallback loaderContactcCallback) {
        this.activity = activity;
        this.loaderContactcCallback = loaderContactcCallback;
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
        if (loaderContactcCallback != null) loaderContactcCallback.onContactsLoaded(data);
        else throw new IllegalStateException("No contactsCallback found!");
    }

    @Override
    public void onLoaderReset(Loader<List<ContactModel>> loader) {

    }
}
