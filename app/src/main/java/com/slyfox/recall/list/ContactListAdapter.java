package com.slyfox.recall.list;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 25.06.2016.
 *
 * Adapter for contacts RecyclerView. We use AdapterDelegates library to manage this
 */
public class ContactListAdapter extends ListDelegationAdapter<List<ContactModel>> {

    public ContactListAdapter(Activity activity, List<ContactModel> contacts) {
        //Add ContactAdapterDelegate to DelegateManager to delegate list item managing to it
        delegatesManager.addDelegate(new ContactAdapterDelegate(activity));
        setItems(contacts); //Keep contacts in adapter
    }
}
