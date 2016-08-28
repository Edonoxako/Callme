package com.slyfox.recall;

import com.slyfox.recall.domain.ContactPresenter;
import com.slyfox.recall.domain.FlowManager;
import com.slyfox.recall.domain.IContactLoader;
import com.slyfox.recall.domain.IContactView;
import com.slyfox.recall.domain.RequestType;
import com.slyfox.recall.model.Contact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;


/**
 * Created by Eugene on 23.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WhenAppStarts {

    private ContactPresenter presenter;
    
    @Mock
    private IContactLoader contactLoader;
    @Mock
    private IContactView contactView;
    @Mock
    private FlowManager flowManager;

    @Before
    public void setUp() throws Exception {
        presenter = new ContactPresenter(contactView, contactLoader, flowManager);
    }

    @Test
    public void shouldLoadContactsListWhenStarts() throws Exception {
        presenter.loadContacts();

        verify(contactLoader).loadContacts(any(IContactLoader.ContactsCallback.class));
    }

    @Test
    public void shouldShowContactsWhenTheyAreLoaded() throws Exception {
        presenter.onContactsLoaded(Collections.<Contact>emptyList());

        verify(contactView).showContacts(anyCollectionOf(Contact.class));
    }

    @Test
    public void shouldGetContactPhoneNumbersWhenAskForCallActionInvoked() throws Exception {
        presenter.askForCall(0);

        verify(contactLoader).loadContactNumbers(anyLong(), any(IContactLoader.NumbersCallback.class));
    }

    @Test
    public void shouldGetContactPhoneNumberWhenAskForMoneyActionInvoked() throws Exception {
        presenter.askForMoney(0);

        verify(contactLoader).loadContactNumbers(anyLong(), any(IContactLoader.NumbersCallback.class));
    }

    @Test
    public void shouldStartCallAskingFlowWhenAskForMoneyActionInvokedAndContactNumbersObtained() throws Exception {
        presenter.askForCall(0);
        presenter.onContactNumbersLoaded(Collections.<String>emptyList());

        verify(flowManager).startAskFlow(eq(RequestType.CALL), (List<String>) anyCollectionOf(String.class));
    }

    @Test
    public void shouldStartMoneyAskingFlowWhenAskForMoneyActionInvokedAndContactNumbersObtained() throws Exception {
        presenter.askForMoney(0);
        presenter.onContactNumbersLoaded(Collections.<String>emptyList());

        verify(flowManager).startAskFlow(eq(RequestType.MONEY), (List<String>) anyCollectionOf(String.class));
    }
}
