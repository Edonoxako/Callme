package com.slyfox.recall.manager;

/**
 * Created by Eugene on 18.07.2016.
 */
public class MobileOperatorManager {

    private String prefixCallCode = "*144*";
    private String prefixMoneyCode = "*143*";

    private String postfixCode = "#";

    public String createCallAskingRequest(String number) {
        return String.format("%s%s%s", prefixCallCode, number, postfixCode);
    }

    public String createMoneyAskingRequest(String number) {
        return String.format("%s%s%s", prefixMoneyCode, number, postfixCode);
    }

}
