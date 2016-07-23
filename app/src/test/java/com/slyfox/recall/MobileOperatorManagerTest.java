package com.slyfox.recall;

import com.slyfox.recall.manager.MobileOperatorManager;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MobileOperatorManagerTest {

    private MobileOperatorManager manager;

    @Before
    public void initMobileOperatorManager() {
        manager = new MobileOperatorManager();
    }

    @Test
    public void testCallAskingMegafonOperator() {
        String callAskingRequest = manager.createCallAskingRequest("+79233576964");
        assertThat(callAskingRequest, is("*144*+79233576964#"));
    }

    @Test
    public void testMoneyAskingMegafonOperator() {
        String callAskingRequest = manager.createMoneyAskingRequest("+79233576964");
        assertThat(callAskingRequest, is("*143*+79233576964#"));
    }
}