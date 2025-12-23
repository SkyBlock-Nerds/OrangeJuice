package net.hypixel.orangejuice.util;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.util.model.Pair;

import java.util.List;

@Log4j2
public class StringUtil {

    private StringUtil() {
    }

    public static Pair<String, Integer> getLongestLine(List<String> strings) {
        String longest = "";
        int length = 0;

        for (String string : strings) {
            if (string.length() > length) {
                longest = string;
                length = string.length();
            }
        }

        return new Pair<>(longest, length);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}