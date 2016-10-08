package com.slyfox.recall;

import com.slyfox.recall.domain.ContactPresenter;
import com.slyfox.recall.domain.permissions.IPermissionView;
import com.slyfox.recall.domain.permissions.PermissionManager;
import com.slyfox.recall.domain.permissions.PermissionsPresenterProxy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by edono on 08.10.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class PermissionsResolving {

    private PermissionsPresenterProxy proxy;

    @Mock
    private ContactPresenter presenter;
    @Mock
    private PermissionManager manager;
    @Mock
    private IPermissionView permissionView;

    private Answer<Void> permissionsGrantedAnswer;

    private Answer<Void> permissionsRefusedAnswer;

    @Before
    public void setUp() throws Exception {
        proxy = new PermissionsPresenterProxy(presenter, manager, permissionView);

        permissionsGrantedAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PermissionManager.Callback callback = (PermissionManager.Callback) invocation.getArguments()[0];
                callback.permissionGranted();
                return null;
            }
        };

        permissionsRefusedAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PermissionManager.Callback callback = (PermissionManager.Callback) invocation.getArguments()[0];
                callback.permissionRefused();
                return null;
            }
        };
    }

    @Test
    public void shouldDelegateLoadingContactsToPresenterWhenContactsPermissionsGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(true);

        proxy.loadContacts();

        verify(presenter).loadContacts();
    }

    @Test
    public void shouldNotDelegateLoadingContactsToPresenterAndResolvePermissionsWhenContactsPermissionsNotGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);

        proxy.loadContacts();

        verify(presenter, never()).loadContacts();
        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
    }

    @Test
    public void shouldDelegateLoadingContactsToPresenterWhenUserGrantsPermissions() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);
        doAnswer(permissionsGrantedAnswer).when(manager).resolvePermissions(any(PermissionManager.Callback.class));

        proxy.loadContacts();

        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
        verify(presenter).loadContacts();
    }

    @Test
    public void shouldShowPermissionsDeniedMessageWhenUserDoesNotGrantPermissions() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);
        doAnswer(permissionsRefusedAnswer).when(manager).resolvePermissions(any(PermissionManager.Callback.class));

        proxy.loadContacts();

        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
        verify(permissionView).showPermissionsDeniedMessage(anyString());
    }

    @Test
    public void shouldDelegateAskForCallToPresenterWhenContactsPermissionGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(true);

        proxy.askForCall(0);

        verify(presenter).askForCall(anyLong());
    }

    @Test
    public void shouldNotDelegateAskForCallToPresenterAndResolvePermissionsWhenContactsPermissionsNotGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);

        proxy.askForCall(0);

        verify(presenter, never()).askForCall(anyLong());
        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
    }

    @Test
    public void shouldDelegateAskForCallToPresenterWhenUserGrantsPermissions() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);
        doAnswer(permissionsGrantedAnswer).when(manager).resolvePermissions(any(PermissionManager.Callback.class));

        proxy.askForCall(0);

        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
        verify(presenter).askForCall(anyLong());
    }

    @Test
    public void shouldDelegateAskForMoneyToPresenterWhenContactsPermissionGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(true);

        proxy.askForMoney(0);

        verify(presenter).askForMoney(anyLong());
    }

    @Test
    public void shouldNotDelegateAskForMoneyToPresenterAndResolvePermissionsWhenContactsPermissionsNotGranted() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);

        proxy.askForMoney(0);

        verify(presenter, never()).askForMoney(anyLong());
        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
    }

    @Test
    public void shouldDelegateAskForMoneyToPresenterWhenUserGrantsPermissions() throws Exception {
        when(manager.contactsPermissionsGranted()).thenReturn(false);
        doAnswer(permissionsGrantedAnswer).when(manager).resolvePermissions(any(PermissionManager.Callback.class));

        proxy.askForMoney(0);

        verify(manager).resolvePermissions(any(PermissionManager.Callback.class));
        verify(presenter).askForMoney(anyLong());
    }

    @Test
    public void shouldDelegateHandlingPermissionRequestResultToPermissionManager() throws Exception {
        proxy.onRequestPermissionsResult(0, new String[] {}, new int[] {});

        verify(manager).onRequestPermissionsResult(anyInt(), any(String[].class), any(int[].class));
    }
}
