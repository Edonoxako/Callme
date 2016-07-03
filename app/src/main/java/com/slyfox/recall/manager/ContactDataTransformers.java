package com.slyfox.recall.manager;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.google.common.base.Function;
import com.slyfox.recall.model.ContactModel;

import java.util.Collections;

/**
 * Created by Eugene on 03.07.2016.
 *
 * This class contains transformers that are used to transform raw cursor
 * contact data to the form, suitable for displaying in UI
 */
public class ContactDataTransformers {

    //Transform single contacts cursor row to an instance of the ContactModel
    public static final Function<Cursor, ContactModel> TO_CONTACT_MODEL = new Function<Cursor, ContactModel>() {
        @Override
        public ContactModel apply(Cursor input) {
            long id = input.getLong(input.getColumnIndex(ContactsContract.Contacts._ID));
            String name = input.getString(input.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            return new ContactModel(id, name, Collections.<String>emptyList());
        }
    };

}
