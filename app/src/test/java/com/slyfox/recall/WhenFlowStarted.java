package com.slyfox.recall;

import com.slyfox.recall.domain.AskingRequestBuilder;
import com.slyfox.recall.domain.FlowManager;
import com.slyfox.recall.domain.INumberQualifier;
import com.slyfox.recall.domain.IPhone;
import com.slyfox.recall.domain.RequestType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Created by Eugene on 23.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class WhenFlowStarted {

    private FlowManager flowManager;

    @Mock
    private AskingRequestBuilder requestBuilder;

    @Mock
    private INumberQualifier numberQualifier;

    @Mock
    private IPhone phone;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        flowManager = new FlowManager(requestBuilder, numberQualifier, phone);
    }

    @Test
    public void shouldThrowExceptionIfThereAreNoNumbersToAsk() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This contact has no phone numbers");

        flowManager.startAskFlow(RequestType.CALL, Collections.<String>emptyList());
    }

    @Test
    public void shouldCreateCallAskingRequestWhenAskingForCall() throws Exception {
        flowManager.startAskFlow(RequestType.CALL, Collections.singletonList("test"));

        verify(requestBuilder).buildRequest(eq(RequestType.CALL), any(String.class));
    }

    @Test
    public void shouldCreateMoneyAskingRequestWhenAskingForMoney() throws Exception {
        flowManager.startAskFlow(RequestType.MONEY, Collections.singletonList("test"));

        verify(requestBuilder).buildRequest(eq(RequestType.MONEY), any(String.class));
    }

    @Test
    public void shouldAskUserToChooseNumberIfTheCountOfNumbersIsMoreThanOne() throws Exception {
        flowManager.startAskFlow(RequestType.CALL, Arrays.asList("123", "123"));
        
        verify(numberQualifier).qualify(anyCollectionOf(String.class), any(INumberQualifier.Callback.class));
    }

    @Test
    public void shouldMakeAskingRequestWithCreatedRequest() throws Exception {
        when(requestBuilder.buildRequest(any(RequestType.class), any(String.class))).thenReturn("*144*11231231234#");

        flowManager.startAskFlow(RequestType.CALL, Collections.singletonList("11231231234"));

        verify(phone).makeRequest(eq("*144*11231231234#"));
    }

    @Test
    public void shouldCreateAskForCallRequestWhenUserWithManyNumbersAskingForCall() throws Exception {
        flowManager.startAskFlow(RequestType.CALL, Arrays.asList("123", "123"));
        flowManager.onQualifiedNumber("123");

        verify(requestBuilder).buildRequest(eq(RequestType.CALL), any(String.class));
    }

    @Test
    public void shouldCreateAskForMoneyRequestWhenUserWithManyNumbersAskingForMoney() throws Exception {
        flowManager.startAskFlow(RequestType.MONEY, Arrays.asList("123", "123"));
        flowManager.onQualifiedNumber("123");

        verify(requestBuilder).buildRequest(eq(RequestType.MONEY), any(String.class));
    }

    @Test
    public void shouldMakeRequestWithNumberSpecifiedByUser() throws Exception {
        when(requestBuilder.buildRequest(any(RequestType.class), any(String.class))).thenReturn("*144*11231231234#");

        flowManager.onQualifiedNumber("11231231234");

        verify(phone).makeRequest(eq("*144*11231231234#"));
    }
}
