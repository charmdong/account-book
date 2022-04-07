package com.accountbook.common.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {

    public static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 14;
    public static final int PLUS_DAY = 14;
    public static final String LOGIN_CHECK_COOKIE = "UID";

    public static Cookie getCookieByName(Cookie[] cookies, String targetName){
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기

                if (name.equals(targetName)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static String getCookieValueByName(Cookie[] cookies, String targetName){
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기

                if (name.equals(targetName)) {
                    return value;
                }
            }
        }
        return null;
    }
}
