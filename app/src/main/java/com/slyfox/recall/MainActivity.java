package com.slyfox.recall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.slyfox.recall.domain.AskingRequestBuilder;
import com.slyfox.recall.domain.ContactPresenter;
import com.slyfox.recall.domain.FlowManager;
import com.slyfox.recall.domain.IContactView;
import com.slyfox.recall.domain.INumberQualifier;
import com.slyfox.recall.list.ContactAdapterDelegate;
import com.slyfox.recall.list.ContactListAdapter;
import com.slyfox.recall.manager.DialogQualifier;
import com.slyfox.recall.manager.Phone;
import com.slyfox.recall.manager.loading.ContactLoadingManager;
import com.slyfox.recall.model.Contact;
import com.slyfox.recall.model.ContactModel;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        ContactAdapterDelegate.AskButtonListener,
        IContactView {

    @BindView(R.id.contactList)
    RecyclerView contactList;

    private ContactPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Init RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contactList.setLayoutManager(layoutManager);

        //Create and init presenter
        ContactLoadingManager contactManager = new ContactLoadingManager(this);
        INumberQualifier qualifier = DialogQualifier.create(getSupportFragmentManager());
        FlowManager flowManager = new FlowManager(new AskingRequestBuilder(), qualifier, new Phone(this));
        presenter = new ContactPresenter(this, contactManager, flowManager);

        //Load all contacts
        presenter.loadContacts();
    }

    @Override
    public void onAskButtonClick(int button, long contactId) {
        if (button == ContactAdapterDelegate.ASK_FOR_CALL_BUTTON) {
            presenter.askForCall(contactId);
        } else {
            presenter.askForMoney(contactId);
        }

    }

    @Override
    public void showContacts(Collection<? extends Contact> contacts) {
        ContactAdapterDelegate delegate = new ContactAdapterDelegate(this, this);
        ContactListAdapter adapter = new ContactListAdapter(FluentIterable
                .from(contacts)
                .transform(new Function<Contact, ContactModel>() {
                    @Override
                    public ContactModel apply(Contact input) {
                        return (ContactModel) input;
                    }
                })
                .toList(),
                delegate);
        contactList.swapAdapter(adapter, true);
    }
}
