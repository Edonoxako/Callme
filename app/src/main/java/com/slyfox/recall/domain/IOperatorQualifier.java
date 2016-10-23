package com.slyfox.recall.domain;

import com.slyfox.recall.exception.UnknownOperatorException;

/**
 * Created by edono on 23.10.2016.
 */
public interface IOperatorQualifier {
    OperatorType fetchOperatorType() throws UnknownOperatorException;
}
