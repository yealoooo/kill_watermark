package cn.video.service.impl;

import cn.video.parse.handler.DouYinParseHandler;
import cn.video.service.PolymericService;
import cn.video.vo.ParseVO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PolymericServiceImpl implements PolymericService {

    @Override
    public ParseVO parseUrl(String url) throws IOException {
        if (url.contains("douyin")) {
            DouYinParseHandler douYinParseHandler = new DouYinParseHandler();
            return douYinParseHandler.parseUrl(url);
        }
        return null;
    }
}
