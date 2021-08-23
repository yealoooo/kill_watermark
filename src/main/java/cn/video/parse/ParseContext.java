package cn.video.parse;

import cn.video.parse.handler.DouYinParseHandler;
import cn.video.parse.handler.PipiXiaParseHandler;
import cn.video.parse.handler.WeiBoParseHandler;
import cn.video.parse.handler.XiaoHongShuParseHandler;

public class ParseContext {

    public static Parse getParser(String url) {
        if (url.contains("douyin")) {
            return new DouYinParseHandler();
        }else if (url.contains("pipi")) {
            return new PipiXiaParseHandler();
        }else if (url.contains("xhs")) {
            return new XiaoHongShuParseHandler();
        }else if (url.contains("weibo")) {
            return new WeiBoParseHandler();
        }
        return null;
    }
}
