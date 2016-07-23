package com.slyfox.recall.manager.loading;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Function;
import com.slyfox.recall.model.ContactModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eugene on 03.07.2016.
 *
 * This class contains transformers that are used to transform raw cursor
 * contact data to the form, suitable for displaying in UI
 */
public class ContactDataTransformers {

    private static final String TAG = "ContactDataTransformers";

    //Transform single contacts cursor row to an instance of the ContactModel
    public static final Function<Cursor, ContactModel> ROW_TO_CONTACT_MODEL = new Function<Cursor, ContactModel>() {
        @Override
        public ContactModel apply(Cursor input) {
            long id = input.getLong(input.getColumnIndex(ContactsContract.Contacts._ID));
            String name = input.getString(input.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            return new ContactModel(id, name, Collections.<String>emptyList());
        }
    };

    public static final Function<Cursor, ContactModel> TO_CONTACT_MODEL = new Function<Cursor, ContactModel>() {
        @Nullable
        @Override
        public ContactModel apply(Cursor input) {
            if (input.getCount() == 0) {
                return null;
            }

            input.moveToFirst();
            long id = input.getLong(input.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String name = input.getString(input.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));

            List<String> numbers = new ArrayList<>();
            do {
                numbers.add(input.getString(input.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            } while (input.moveToNext());

            ContactModel contactModel = new ContactModel(id, name, numbers);
            return contactModel;
        }
    };

}
