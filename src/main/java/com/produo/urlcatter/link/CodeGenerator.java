package com.produo.urlcatter.link;

import java.util.Calendar;
import java.util.Objects;

public class CodeGenerator {
    private String lastCode = "";
    private int additionalNum = 1;
    private static final String KEY_CHAIN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String getCode() {
        Calendar rightNow = Calendar.getInstance();
        String code = "";
        String year = String.valueOf(rightNow.get(Calendar.YEAR)).substring(2);
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        String minutes = String.valueOf(rightNow.get(Calendar.MINUTE));
        String milliseconds = String.valueOf(rightNow.get(Calendar.MILLISECOND));
        code += KEY_CHAIN.charAt(Character.getNumericValue(year.charAt(0)));
        code += KEY_CHAIN.charAt(Character.getNumericValue(year.charAt(1)));
        code += KEY_CHAIN.charAt(rightNow.get(Calendar.MONTH));
        code += KEY_CHAIN.charAt(rightNow.get(Calendar.DAY_OF_MONTH));
        code += KEY_CHAIN.charAt(hour + (int) Math.round(Double.parseDouble(minutes) / 2));
        code += KEY_CHAIN.charAt(rightNow.get(Calendar.SECOND));
        code += KEY_CHAIN.charAt((int) Math.round(Double.parseDouble(milliseconds) / 20));
        if (Objects.equals(lastCode, code)) {
            code += String.valueOf(additionalNum);
            additionalNum++;
        } else {
            additionalNum = 1;
        }
        lastCode = code;
        return code;
    }
}
