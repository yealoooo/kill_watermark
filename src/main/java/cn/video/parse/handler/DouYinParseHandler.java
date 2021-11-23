package cn.video.parse.handler;

import cn.hutool.core.util.URLUtil;
import cn.video.controller.vo.ParseVO;
import cn.video.parse.Parse;
import cn.video.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class DouYinParseHandler extends Parse {

    @Override
    public ParseVO parseUrl(String url) {
        // 获取 重定向地址
        Map<String, String> headMap = getHeaderMap();
        String locationUrl = HttpUtil.getLocationUrl(url, headMap);

        String itemId = getItemId(locationUrl);
        String responseBody = HttpUtil.getBody(getRequestUrl() + itemId, headMap);

        JSONObject responseBodyJsonObj = JSON.parseObject(responseBody);

        JSONObject itemListJsonObj = responseBodyJsonObj.getJSONArray("item_list").getJSONObject(0);

        JSONObject videoObject = itemListJsonObj.getJSONObject("video");

        String videoUrl = videoObject.getJSONObject("play_addr").getJSONArray("url_list").getString(0).replace("playwm", "play");

        // 获取视频的重定向地址ss

        String locationVideoUrl = HttpUtil.getLocationUrl(videoUrl, headMap);
        return ParseVO.video(videoUrl, "https://watermark.doobird.cn/polymeric/downLoadImage?url=" + URLUtil.encode(locationVideoUrl), true);
    }


    @Override
    public String getItemId(String url) {
        int start = url.indexOf("/video/") + "/video/".length();
        int end = url.lastIndexOf("/");

        return url.substring(start, end);
    }

    @Override
    public String getRequestUrl() {
        return "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return super.getHeaderMap();
    }

    public static void main(String[] args) throws IOException {
        DouYinParseHandler douYinParseHandler = new DouYinParseHandler();
        douYinParseHandler.parseUrl("https://v.douyin.com/eEcPdeo/");
    }
}
