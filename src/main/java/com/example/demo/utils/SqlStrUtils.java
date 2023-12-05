package com.example.demo.utils;

public class SqlStrUtils {

    //    拼接括号 (xx,xx,xx) ---> 用于 IN (xx,xx,xx)  实现动态多选
    public static String MutiSelection (String str){
        String[] stringArray = str.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int strLen = stringArray.length;
        for (int i = 0; i < strLen; i++) {
            sb.append("'");
            sb.append(stringArray[i]);
            sb.append("'");
            if (i!=(strLen-1)){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
