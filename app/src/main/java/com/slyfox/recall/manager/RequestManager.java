package com.slyfox.recall.manager;

import android.content.Context;
import android.util.Log;

import com.slyfox.recall.model.ContactModel;

/**
 * Created by Eugene on 09.07.2016.
 */
public class RequestManager implements SingleContactsCallback.OnSingleContactLoadedCallback {

    private static final String TAG = "RequestManager";

    private Context context;
    private ContactLoadingManager loadingManager;

    public RequestManager(Context context, ContactLoadingManager loadingManager) {
        this.context = context;
        this.loadingManager = loadingManager;
    }

    public void startCallAskingFlow(long userId) {
        loadingManager.loadSingleContact(userId, this);
    }

    public void startMoneyAskingFlow(long userId) {

    }

    @Override
    public void onSingleContactLoaded(ContactModel contact) {
        if (contact == null) {
            Log.e(TAG, "onSingleContactLoaded: no contact obtained");
            return;
        }
        Log.d(TAG, "onSingleContactLoaded: " + contact);
    }
}
