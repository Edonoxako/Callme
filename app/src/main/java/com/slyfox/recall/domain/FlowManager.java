package com.slyfox.recall.domain;

import java.util.List;

/**
 * Created by Eugene on 23.07.2016.
 */
public class FlowManager implements INumberQualifier.Callback {
    private AskingRequestBuilder builder; //builds requests aka *144*<number>#
    private INumberQualifier numberQualifier; //is used to let user to specify the exact number if he has more then one
    private IPhone phone;

    private RequestType flowType; //MONEY or CALL

    public FlowManager(AskingRequestBuilder builder, INumberQualifier numberQualifier, IPhone phone) {
        this.builder = builder;
        this.numberQualifier = numberQualifier;
        this.phone = phone;
    }

    public void startAskFlow(RequestType requestType, List<String> numbers) {
        throwIfNumbersIsEmpty(numbers);

        if (numbers.size() > 1) {
            flowType = requestType;
            numberQualifier.qualify(numbers, this);
        } else {
            makeAskRequest(requestType, numbers.get(0));
        }
    }

    @Override
    public void onQualifiedNumber(String number) {
        makeAskRequest(flowType, number);
    }

    private void makeAskRequest(RequestType flowType, String number) {
        String request = builder.buildRequest(flowType, number);
        if (request != null) phone.makeRequest(request);
    }

    private void throwIfNumbersIsEmpty(List<String> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("This contact has no phone numbers");
        }
    }
}
