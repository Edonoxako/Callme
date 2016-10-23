package com.slyfox.recall.domain;

/**
 * Created by edono on 23.10.2016.
 */
public enum OperatorType {
    MEGAFON, MTS, BEELINE, YOTA;

    public static OperatorType from(String name) {
        switch (name) {
            case "MegaFon":
                return MEGAFON;
            case "MTS":
                return MTS;
            case "Beeline":
                return BEELINE;
            case "Yota":
                return YOTA;
            default:
                return null;
        }
    }
}
