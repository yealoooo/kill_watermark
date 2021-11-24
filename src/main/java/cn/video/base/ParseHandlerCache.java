package cn.video.base;

import cn.hutool.extra.spring.SpringUtil;
import cn.video.parse.Parse;

import java.util.HashMap;
import java.util.Map;

public class ParseHandlerCache {
    private static final Map<String, Parse> parseHandlerMap = new HashMap<>();

    public static void setParseHandler(String keyword, String beanName) {

        Parse parseHandler = SpringUtil.getBean(beanName);

        parseHandlerMap.put(keyword, parseHandler);
    }

    public static Parse getParseHandler(String keyword) {
        return parseHandlerMap.get(keyword);
    }
}
