package com.sparta.assignment.security;

import java.util.Objects;

public class ValidCheck {
    public static void procRequestUserValidCheck(Long id, UserDetailsImpl userDetails){
        // 요청자가 원래의 데이터 주인과 다른 사람일 경우 throw
        // Exception type : SecurityException
        if(!Objects.equals(id, userDetails.getUserId())){
            throw new SecurityException("앞으론 이러지 마세요... ^^");
        }
    }

    public static String cleanXSS(String value) {
        // XSS 방어 구문
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}
