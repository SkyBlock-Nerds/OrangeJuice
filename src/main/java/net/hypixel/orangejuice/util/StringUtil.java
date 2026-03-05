package net.hypixel.orangejuice.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringUtil {

    private StringUtil() {}

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}