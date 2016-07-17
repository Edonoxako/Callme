package com.slyfox.recall.manager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.getbase.android.db.loaders.CursorLoaderBuilder;
import com.slyfox.recall.model.ContactModel;

import static com.slyfox.recall.manager.ContactDataTransformers.TO_CONTACT_MODEL;

/**
 * Created by Eugene on 17.07.2016.
 */
public class SingleContactsCallback implements LoaderManager.LoaderCallbacks<ContactModel> {

    public static final int LOADER_ID = 1; //loader id

    public interface OnSingleContactLoadedCallback {
        void onSingleContactLoaded(ContactModel contact);
    }

    private AppCompatActivity activity;
    private OnSingleContactLoadedCallback singleContactCallback;

    private long mContactId;

    public SingleContactsCallback(AppCompatActivity activity, long mContactId, OnSingleContactLoadedCallback singleContactCallback) {
        this.activity = activity;
        this.mContactId = mContactId;
        this.singleContactCallback = singleContactCallback;
    }

    @Override
    public Loader<ContactModel> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return CursorLoaderBuilder.forUri(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                    .projection(ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER)
                    .where(ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", mContactId)
                    .transform(TO_CONTACT_MODEL) //transform from cursor to list
                    .build(activity);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ContactModel> loader, ContactModel data) {
        //There MUST be contactsCallback to return loading result
        if (singleContactCallback != null) singleContactCallback.onSingleContactLoaded(data);
        else throw new IllegalStateException("No singleContactCallback found!");
    }

    @Override
    public void onLoaderReset(Loader<ContactModel> loader) {

    }

}
