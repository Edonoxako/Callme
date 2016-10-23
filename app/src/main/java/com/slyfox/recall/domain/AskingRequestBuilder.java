package com.slyfox.recall.domain;

import com.slyfox.recall.domain.RequestType;
import com.slyfox.recall.exception.UnknownOperatorException;

import java.util.Map;

/**
 * Created by Eugene on 23.07.2016.
 *
 * Builds the call/money request depending on demanded type and
 * mobile operator
 */
public class AskingRequestBuilder {

    private IOperatorQualifier qualifier;

    private IServiceCodesRepository repository;

    private IExceptionHandler exceptionHandler;

    public AskingRequestBuilder(IOperatorQualifier qualifier, IServiceCodesRepository repository, IExceptionHandler exceptionHandler) {
        this.qualifier = qualifier;
        this.repository = repository;
        this.exceptionHandler = exceptionHandler;
    }

    public String buildRequest(RequestType type, String number) {
        try {
            OperatorType operatorType = qualifier.fetchOperatorType();
            Map<RequestType, String> codes = repository.getCodesByOperatorType(operatorType);
            if (codes == null) throw new RuntimeException("Codes for operator of type: " + operatorType + " are null");
            return String.format("%s%s%s%s%s", "*", codes.get(type), "*", number, "#");
        } catch (UnknownOperatorException e) {
            exceptionHandler.handleException(e);
            return null;
        }
    }
}
