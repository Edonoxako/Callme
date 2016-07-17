package com.slyfox.recall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.slyfox.recall.list.ContactAdapterDelegate;
import com.slyfox.recall.list.ContactListAdapter;
import com.slyfox.recall.manager.AllContactsCallback;
import com.slyfox.recall.manager.ContactLoadingManager;
import com.slyfox.recall.manager.RequestManager;
import com.slyfox.recall.model.ContactModel;
import com.slyfox.recall.repository.ContactsRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        AllContactsCallback.OnContactsLoadedCallback,
        ContactAdapterDelegate.AskButtonListener {

    @BindView(R.id.contactList)
    RecyclerView contactList;

    private ContactLoadingManager contactManager;
    private ContactsRepository repository;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contactList.setLayoutManager(layoutManager);

        contactManager = new ContactLoadingManager(this);
        requestManager = new RequestManager(this, contactManager);

        contactManager.loadContacts(this);
    }

    @Override
    public void onContactsLoaded(List<ContactModel> contacts) {
        repository = new ContactsRepository(contacts);

        ContactAdapterDelegate delegate = new ContactAdapterDelegate(this, this);
        ContactListAdapter adapter = new ContactListAdapter(repository.getContacts(), delegate);
        contactList.swapAdapter(adapter, true);
    }

    @Override
    public void onAskButtonClick(int button, long contactId) {
        ContactModel contact = repository.findContactById(contactId);
        if (button == ContactAdapterDelegate.ASK_FOR_CALL_BUTTON) {
            requestManager.startCallAskingFlow(contactId);
        } else {
            Toast.makeText(this, "Ask for money: " + contact.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
