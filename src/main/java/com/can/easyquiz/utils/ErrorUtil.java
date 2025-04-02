package com.can.easyquiz.utils;

public class ErrorUtil {
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }
}
