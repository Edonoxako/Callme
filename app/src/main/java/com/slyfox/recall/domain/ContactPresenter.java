package com.slyfox.recall.domain;

import com.slyfox.recall.model.Contact;

import java.util.List;

/**
 * Created by Eugene on 23.07.2016.
 */
public class ContactPresenter implements IContactLoader.ContactsCallback, IContactLoader.NumbersCallback {
    private IContactView contactView;
    private IContactLoader contactLoader;
    private FlowManager flowManager;

    private RequestType initiatedRequest;

    public ContactPresenter(IContactView contactView, IContactLoader contactLoader, FlowManager flowManager) {
        this.contactView = contactView;
        this.contactLoader = contactLoader;
        this.flowManager = flowManager;
    }

    public void loadContacts() {
        contactLoader.loadContacts(this);
    }

    public void askForCall(long contactId) {
        initiatedRequest = RequestType.CALL;
        contactLoader.loadContactNumbers(contactId, this);
    }

    public void askForMoney(long contactId) {
        initiatedRequest = RequestType.MONEY;
        contactLoader.loadContactNumbers(contactId, this);
    }

    @Override
    public void onContactsLoaded(List<? extends Contact> contacts) {
        contactView.showContacts(contacts);
    }

    @Override
    public void onContactNumbersLoaded(List<String> numbers) {
        flowManager.startAskFlow(initiatedRequest, numbers);
    }
}
