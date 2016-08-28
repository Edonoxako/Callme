package com.slyfox.recall.manager.loading;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.getbase.android.db.provider.ProviderAction;
import com.slyfox.recall.domain.IContactLoader;

import java.util.List;

import static com.slyfox.recall.manager.loading.ContactDataTransformers.ROW_TO_NUMBER_STRING;

/**
 * Created by edono on 28.08.2016.
 */
public class ContactNumbersTask extends AsyncTask<Void, Void, List<String>> {

    public static final int LOADER_ID = 2;

    private Context context;
    private IContactLoader.NumbersCallback callback;
    private long mContactId;

    public ContactNumbersTask(Context context, long mContactId, IContactLoader.NumbersCallback callback) {
        this.context = context;
        this.mContactId = mContactId;
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        return ProviderAction.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                .projection(ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER)
                .where(ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", mContactId)
                .perform(context.getContentResolver())
                .toFluentIterable(ROW_TO_NUMBER_STRING)
                .toList();
    }

    @Override
    protected void onPostExecute(List<String> data) {
        super.onPostExecute(data);
        //There MUST be contactsCallback to return loading result
        if (callback != null) callback.onContactNumbersLoaded(data);
        else throw new IllegalStateException("No IContactLoader.NumbersCallback found!");
    }
}
