package com.slyfox.recall.list;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 25.06.2016.
 */
public class ContactListAdapter extends ListDelegationAdapter<List<ContactModel>> {

    public ContactListAdapter(Activity activity, List<ContactModel> contacts) {
        delegatesManager.addDelegate(new ContactAdapterDelegate(activity));
        setItems(contacts);
    }
}
