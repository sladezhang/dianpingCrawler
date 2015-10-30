package cn.edu.fudan.dianping.crawler;

import java.util.Map;

/**
 * Created by Dawnwords on 2015/10/30.
 */
public class Cookie {
    private static Map<String, String> cookie;

    public static Map<String, String> cookie() {
        return cookie;
    }

    public static void cookie(Map<String, String> cookie) {
        Cookie.cookie = cookie;
    }

    public static void clearCookie(){
        cookie = null;
    }
}
