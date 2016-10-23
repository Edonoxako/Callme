package com.slyfox.recall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.slyfox.recall.domain.AskingRequestBuilder;
import com.slyfox.recall.domain.ContactPresenter;
import com.slyfox.recall.domain.FlowManager;
import com.slyfox.recall.domain.IContactView;
import com.slyfox.recall.domain.INumberQualifier;
import com.slyfox.recall.domain.permissions.IPermissionView;
import com.slyfox.recall.domain.permissions.PermissionsPresenterProxy;
import com.slyfox.recall.list.ContactAdapterDelegate;
import com.slyfox.recall.list.ContactListAdapter;
import com.slyfox.recall.manager.DialogQualifier;
import com.slyfox.recall.manager.handler.ExceptionHandler;
import com.slyfox.recall.manager.operator.MobileCodesRepository;
import com.slyfox.recall.manager.operator.MobileOperatorQualifier;
import com.slyfox.recall.manager.permissions.DynamicPermissionManager;
import com.slyfox.recall.manager.Phone;
import com.slyfox.recall.manager.loading.ContactLoadingManager;
import com.slyfox.recall.model.Contact;
import com.slyfox.recall.model.ContactModel;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        ContactAdapterDelegate.AskButtonListener,
        IContactView,
        IPermissionView {

    @BindView(R.id.contactList)
    RecyclerView contactList;
    @BindView(R.id.mainActivity)
    View activityView;

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

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        MobileOperatorQualifier operatorQualifier = new MobileOperatorQualifier(telephonyManager);
        MobileCodesRepository codesRepository = new MobileCodesRepository();
        ExceptionHandler exceptionHandler = new ExceptionHandler(this);
        FlowManager flowManager = new FlowManager(new AskingRequestBuilder(operatorQualifier, codesRepository, exceptionHandler), qualifier, new Phone(this));

        ContactPresenter contactPresenter = new ContactPresenter(this, contactManager, flowManager);
        presenter = new PermissionsPresenterProxy(contactPresenter, new DynamicPermissionManager(this, activityView), this);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (presenter instanceof PermissionsPresenterProxy) {
            ((PermissionsPresenterProxy) presenter).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showPermissionsDeniedMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
