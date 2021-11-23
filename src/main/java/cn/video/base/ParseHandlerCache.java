package cn.video.base;

import cn.video.parse.Parse;
import cn.video.util.ApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;

public class ParseHandlerCache {
    private static final Map<String, Parse> parseHandlerMap = new HashMap<>();

    public static void setParseHandler(String keyword, String beanName) {
        Parse parseHandler = (Parse) ApplicationContextUtils.getBean(beanName);

        parseHandlerMap.put(keyword, parseHandler);
    }

    public static Parse getParseHandler(String keyword) {
        return parseHandlerMap.get(keyword);
    }
}
