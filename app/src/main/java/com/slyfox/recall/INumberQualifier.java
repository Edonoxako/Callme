package com.slyfox.recall;

import java.util.Collection;

/**
 * Created by Eugene on 23.07.2016.
 */
public interface INumberQualifier {
    interface Callback {
        void onQualifiedNumber(String number);
    }

    void qualify(Collection<String> numbers, Callback callback);
}
