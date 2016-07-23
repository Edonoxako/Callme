package com.slyfox.recall;

import android.content.Context;

import com.slyfox.recall.manager.MobileOperatorManager;
import com.slyfox.recall.manager.RequestManager;
import com.slyfox.recall.manager.loading.ContactLoadingManager;
import com.slyfox.recall.manager.loading.SingleContactsCallback;
import com.slyfox.recall.model.ContactModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by Eugene on 18.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestManagerTest {

    private RequestManager requestManager;

    @Mock
    private ContactLoadingManager loadingManager;
    @Mock
    private MobileOperatorManager operatorManager;

    @Before
    public void initRequestManager() {
        Context context = mock(Context.class);
        requestManager = new RequestManager(context, loadingManager, operatorManager);
    }

    @Test
    public void testStartCallAskingFlow() {
        requestManager.startCallAskingFlow(1);
        verify(loadingManager).loadSingleContact(anyLong(), any(SingleContactsCallback.OnSingleContactLoadedCallback.class));
    }

    @Test
    public void testStartMoneyAskingFlow() {
        requestManager.startMoneyAskingFlow(1);
        verify(loadingManager).loadSingleContact(anyLong(), any(SingleContactsCallback.OnSingleContactLoadedCallback.class));
    }

    @Test
    public void testOnSingleContactLoaded_nullableContact() {
        ContactModel model = null;
        requestManager.onSingleContactLoaded(model);
        verify(requestManager, never()).showChooseNumberDialog(anyList());
    }
}
