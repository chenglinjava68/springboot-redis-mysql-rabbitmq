package com.liuahang.repo.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String EMPTY = "";

    public static String generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", EMPTY);
    }

    public static String join(String[] fields, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; ++i) {
            if (0 < i) {
                sb.append(separator);
            }
            sb.append(fields[i]);
        }
        return sb.toString();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        return (null == str || 0 >= str.trim().length());
    }

    /**
     * 过滤JavaScript、VBScript、 Active等攻击代码，如果攻击成功，他们可以盗取用户帐户，修改用户设置，盗取/污染cookie等。
     * @param target
     * @return
     */
    public static String getSafeInput(String target) {
        Pattern pattern = Pattern.compile("(?i)[|$@<>()\\\\#]|%7C|%24|%40|%3C|%3E|%28|%29|%5C|%23");
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            target=target.replaceAll("(?i)[|$@<>()\\\\#]|%7C|%24|%40|%3C|%3E|%28|%29|%5C|%23","");
        }
        return target;
    }

}
