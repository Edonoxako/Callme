package com.slyfox.recall;

import com.slyfox.recall.domain.AskingRequestBuilder;
import com.slyfox.recall.domain.IExceptionHandler;
import com.slyfox.recall.domain.IOperatorQualifier;
import com.slyfox.recall.domain.IServiceCodesRepository;
import com.slyfox.recall.domain.OperatorType;
import com.slyfox.recall.domain.RequestType;
import com.slyfox.recall.exception.UnknownOperatorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by edono on 23.10.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class WhenRequestBuilds {

    private AskingRequestBuilder requestBuilder;

    @Mock
    private IOperatorQualifier operatorQualifier;

    @Mock
    private IServiceCodesRepository serviceCodesRepository;

    @Mock
    private IExceptionHandler exceptionHandler;

    @Before
    public void setUp() throws Exception {
        requestBuilder = new AskingRequestBuilder(operatorQualifier, serviceCodesRepository, exceptionHandler);
    }

    @Test
    public void shouldQualifyOperatorType() throws Exception {
        requestBuilder.buildRequest(RequestType.CALL, "1234567890");

        verify(operatorQualifier).fetchOperatorType();
    }

    @Test
    public void shouldGetServiceCodesOfFetchedOperatorFromServiceCodesRepository() throws Exception {
        when(operatorQualifier.fetchOperatorType()).thenReturn(OperatorType.MEGAFON);

        requestBuilder.buildRequest(RequestType.CALL, "1234567890");

        verify(serviceCodesRepository).getCodesByOperatorType(eq(OperatorType.MEGAFON));
    }

    @Test
    public void shouldUseCodesObtainedFromServiceCodesRepositoryToBuildCallRequest() throws Exception {
        Map<RequestType, String> serviceCodes = new HashMap<>();
        serviceCodes.put(RequestType.CALL, "100");
        when(operatorQualifier.fetchOperatorType()).thenReturn(OperatorType.MEGAFON);
        when(serviceCodesRepository.getCodesByOperatorType(eq(OperatorType.MEGAFON))).thenReturn(serviceCodes);

        String resultRequest = requestBuilder.buildRequest(RequestType.CALL, "1234567890");

        assertThat(resultRequest, containsString("*100*"));
    }

    @Test
    public void shouldUseCodesObtainedFromServiceCodesRepositoryToBuildMoneyRequest() throws Exception {
        Map<RequestType, String> serviceCodes = new HashMap<>();
        serviceCodes.put(RequestType.MONEY, "123");
        when(operatorQualifier.fetchOperatorType()).thenReturn(OperatorType.MEGAFON);
        when(serviceCodesRepository.getCodesByOperatorType(eq(OperatorType.MEGAFON))).thenReturn(serviceCodes);

        String resultRequest = requestBuilder.buildRequest(RequestType.MONEY, "1234567890");

        assertThat(resultRequest, containsString("*123*"));
    }

    @Test
    public void shouldDelegateExceptionToExceptionHandlerIfUnknownOperatorExceptionRises() throws Exception {
        when(operatorQualifier.fetchOperatorType()).thenThrow(new UnknownOperatorException("unknown"));

        requestBuilder.buildRequest(RequestType.CALL, "1234567890");

        verify(exceptionHandler).handleException(any(UnknownOperatorException.class));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfTheCodesForQualifiedOperatorAreNull() throws Exception {
        when(operatorQualifier.fetchOperatorType()).thenReturn(OperatorType.MEGAFON);
        when(serviceCodesRepository.getCodesByOperatorType(any(OperatorType.class))).thenReturn(null);

        requestBuilder.buildRequest(RequestType.CALL, "1234567890");
    }
}
