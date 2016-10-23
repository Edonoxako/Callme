package com.slyfox.recall.exception;

/**
 * Created by edono on 23.10.2016.
 */

public class UnknownOperatorException extends Exception {

    public UnknownOperatorException(String operator) {
        super("Unknown operator: " + operator);
    }

}
