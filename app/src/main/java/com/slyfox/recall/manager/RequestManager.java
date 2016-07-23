package com.slyfox.recall.manager;

import android.content.Context;
import android.util.Log;

import com.slyfox.recall.manager.loading.ContactLoadingManager;
import com.slyfox.recall.manager.loading.SingleContactsCallback;
import com.slyfox.recall.model.ContactModel;

import java.util.List;

/**
 * Created by Eugene on 09.07.2016.
 */
public class RequestManager implements SingleContactsCallback.OnSingleContactLoadedCallback {

    private static final String TAG = "RequestManager";

    private Context context;
    private ContactLoadingManager loadingManager;
    private MobileOperatorManager operatorManager;

    public RequestManager(Context context, ContactLoadingManager loadingManager, MobileOperatorManager operatorManager) {
        this.context = context;
        this.loadingManager = loadingManager;
        this.operatorManager = operatorManager;
    }

    public void startCallAskingFlow(long userId) {
        loadingManager.loadSingleContact(userId, this);
    }

    public void startMoneyAskingFlow(long userId) {

    }

    @Override
    public void onSingleContactLoaded(ContactModel contact) {
        if (contact == null || contact.getNumbers() == null) {
            return;
        }

        if (contact.getNumbers().size() > 1) {
            showChooseNumberDialog(contact.getNumbers());
        } else {
            createAndSendRequest(contact.getNumbers().get(0));
        }
    }

    public void showChooseNumberDialog(List<String> numbers) {

    }

    public void createAndSendRequest(String number) {

    }
}
