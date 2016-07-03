package com.slyfox.recall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slyfox.recall.list.ContactListAdapter;
import com.slyfox.recall.manager.ContactLoadingManager;
import com.slyfox.recall.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ContactLoadingManager.OnContactsLoadedCallback {

    @BindView(R.id.contactList)
    RecyclerView contactList;

    private ContactLoadingManager contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contactList.setLayoutManager(layoutManager);

        contactManager = new ContactLoadingManager(this);
        contactManager.loadContacts(this);
    }

    @Override
    public void onContactsLoaded(List<ContactModel> contacts) {
        ContactListAdapter adapter = new ContactListAdapter(this, contacts);
        contactList.swapAdapter(adapter, true);
    }
}
