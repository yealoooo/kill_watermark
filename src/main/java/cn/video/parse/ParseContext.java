package cn.video.parse;

import cn.video.base.ParseHandlerCache;

public class ParseContext {

    public static Parse getParser(String domain) {
        return ParseHandlerCache.getParseHandler(domain);
    }
}
