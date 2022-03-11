package com.produo.urlcatter.utils;

import java.util.Calendar;
import java.util.Objects;

public class CodeGenerator {
    private Calendar now;
    private String lastCode = "";
    private int additionalNum = 1;
    private static final String KEY_CHAIN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generate() {
        now = Calendar.getInstance();
        String code = "";
        code += getFromYear();
        code += getFromMonth();
        code += getFromDay();
        code += getFromHour();
        code += getFromMinutes();
        code += getFromMilliseconds();
        if (Objects.equals(lastCode, code)) {
            code += String.valueOf(additionalNum);
            additionalNum++;
        } else {
            additionalNum = 1;
        }
        lastCode = code;
        return code;
    }

    String getFromYear() {
        String year = String.valueOf(now.get(Calendar.YEAR)).substring(2);
        return "" +
            KEY_CHAIN.charAt(Character.getNumericValue(year.charAt(0))) +
            KEY_CHAIN.charAt(Character.getNumericValue(year.charAt(1)));
    }

    String getFromMonth() {
        return String.valueOf(KEY_CHAIN.charAt(now.get(Calendar.MONTH)));
    }

    String getFromDay() {
        return String.valueOf(KEY_CHAIN.charAt(now.get(Calendar.DAY_OF_MONTH)));
    }

    String getFromHour() {
        int hour = now.get(Calendar.HOUR_OF_DAY);
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        return String.valueOf(KEY_CHAIN.charAt(hour + (int) Math.round(Double.parseDouble(minutes) / 2)));
    }

    String getFromMinutes() {
        return String.valueOf(KEY_CHAIN.charAt(now.get(Calendar.SECOND)));
    }

    String getFromMilliseconds() {
        String milliseconds = String.valueOf(now.get(Calendar.MILLISECOND));
        return String.valueOf(KEY_CHAIN.charAt((int) Math.round(Double.parseDouble(milliseconds) / 20)));
    }
}
