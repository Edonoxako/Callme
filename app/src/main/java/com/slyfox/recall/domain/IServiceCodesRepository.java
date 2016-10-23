package com.slyfox.recall.domain;

import java.util.Map;

/**
 * Created by edono on 23.10.2016.
 */
public interface IServiceCodesRepository {
    Map<RequestType, String> getCodesByOperatorType(OperatorType type);
}
