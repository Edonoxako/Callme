package com.slyfox.recall.manager.operator;

import android.telephony.TelephonyManager;

import com.slyfox.recall.domain.IOperatorQualifier;
import com.slyfox.recall.domain.OperatorType;
import com.slyfox.recall.exception.UnknownOperatorException;

/**
 * Created by edono on 23.10.2016.
 */

public class MobileOperatorQualifier implements IOperatorQualifier {

    private TelephonyManager telephonyManager;

    public MobileOperatorQualifier(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    @Override
    public OperatorType fetchOperatorType() throws UnknownOperatorException {
        String simOperatorName = telephonyManager.getSimOperatorName();
        OperatorType operatorType = OperatorType.from(simOperatorName);
        if (operatorType == null) throw new UnknownOperatorException(simOperatorName);
        return operatorType;
    }

}
