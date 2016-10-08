package com.slyfox.recall.domain;

import com.slyfox.recall.domain.RequestType;

/**
 * Created by Eugene on 23.07.2016.
 *
 * Builds the call/money request depending on demanded type and
 * mobile operator
 */
public class AskingRequestBuilder {

    //TODO: build requests depending on defined operator
    public String buildRequest(RequestType type, String number) {
        switch (type) {
            case CALL:
                return String.format("%s%s%s", "*144*", number, "#");

            case MONEY:
                return String.format("%s%s%s", "*143*", number, "#");

            default:
                throw new IllegalArgumentException("Request type is not recognized");
        }
    }
}
