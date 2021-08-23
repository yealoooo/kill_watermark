package cn.video.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
    public static String filterUrl(String url) {
        // 匹配网址
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&%\\$#_]*)?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return url.substring(m.start(), m.end());
        }
        return null;
    }

    public static String getDomain(String url) {
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return url.substring(m.start(), m.end());
        }
        return null;
    }
}
