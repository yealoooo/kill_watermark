package cn.video.parse.handler;

import cn.video.parse.Parse;
import cn.video.util.HttpRequestHeaderUtil;
import cn.video.util.HttpUtil;
import cn.video.vo.ParseVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PipiXiaParseHandler implements Parse {

    private static final String ItemUrl = "https://is.snssdk.com/bds/cell/detail/?cell_type=1&aid=1319&app_name=super&cell_id=";

    @Override
    public ParseVO parseUrl(String url) throws IOException {

        // 获取 重定向地址
        Map<String, String> headMap = getHeaderMap();
        String locationUrl = HttpUtil.getLocationUrl(url, headMap);

        String itemId = getItemId(locationUrl);
        String responseBody = HttpUtil.getBody(ItemUrl + itemId, headMap);

        JSONObject responseBodyJsonObj = JSON.parseObject(responseBody).getJSONObject("data").getJSONObject("data").getJSONObject("item");

        JSONObject videoObject = responseBodyJsonObj
                .getJSONObject("origin_video_download");

        String videoUrl = videoObject.getJSONArray("url_list").getJSONObject(0).getString("url");

        return ParseVO.video(videoUrl, videoUrl, true);
    }

    @Override
    public String getItemId(String url) {
        int start = url.indexOf("/item/") + "/item/".length();
        int end = url.lastIndexOf("?");
        return url.substring(start, end);
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return new HashMap<String, String>(){{
            put("user-agent", HttpRequestHeaderUtil.getAgent());
        }};
    }
}
