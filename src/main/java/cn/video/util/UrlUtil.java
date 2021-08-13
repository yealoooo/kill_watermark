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

    public static void main(String[] args) {
        String data = "0.28 eBg:/ %dou来cba %杨鸣 %篮球 %裁判 杨鸣挑挑战广东24秒进攻违例，可恶，被他装到了！  https://v.douyin.com/eo5YNoB/ 复制此lian接，答鐦Dou音搜索，直接观kan视频！";

        System.out.println(filterUrl(data));
    }
}
