package com.slyfox.recall.manager.operator;

import com.slyfox.recall.domain.IServiceCodesRepository;
import com.slyfox.recall.domain.OperatorType;
import com.slyfox.recall.domain.RequestType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edono on 23.10.2016.
 */

public class MobileCodesRepository implements IServiceCodesRepository {

    @Override
    public Map<RequestType, String> getCodesByOperatorType(OperatorType type) {
        switch (type) {
            case MEGAFON:
                return assembleCodes("144", "143");

            case MTS:
                return assembleCodes("110", "116");

            case BEELINE:
                return assembleCodes("144", "143");

            case YOTA:
                return assembleCodes("144", "143");

            default:
                return null;
        }
    }

    private Map<RequestType, String> assembleCodes(String call, String money) {
        Map<RequestType, String> serviceCodes = new HashMap<>();
        serviceCodes.put(RequestType.CALL, call);
        serviceCodes.put(RequestType.MONEY, money);
        return serviceCodes;
    }
}
