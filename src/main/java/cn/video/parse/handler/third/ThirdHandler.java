package cn.video.parse.handler.third;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.video.controller.vo.ParseVO;
import cn.video.exceptions.BasicException;
import cn.video.parse.Parse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThirdHandler extends Parse {
    @Override
    public ParseVO parseUrl(String url) throws Exception {

        String body = HttpUtil.get("http://127.0.0.1:8080/hw/kill-water/unified-parse/shareUrl?shareUrl=" + url);

        if (null == body) {
            throw new BasicException(500, "解析失败");
        }

        JSONObject bodyObj = JSON.parseObject(body);

        int code = bodyObj.getIntValue("code");
        if (code != 200) {
            throw new BasicException(500, "解析失败");
        }

        JSONObject dataObj = bodyObj.getJSONObject("body");
        int type = dataObj.getIntValue("type");

        if (type == 1) {
            List<String> imageUrlList = dataObj.getJSONObject("imageParseVO").getJSONArray("imgList").stream().map(x -> {
                return ((JSONObject) x).getString("url");
            }).collect(Collectors.toList());
            return ParseVO.image(imageUrlList, true);
        }else if (type == 2) {
            String videoUrl = dataObj.getJSONObject("videoParseVO").getString("url");
            return ParseVO.video(videoUrl, videoUrl, true);
        }

        return null;
    }

    @Override
    public String getItemId(String url) {
        return null;
    }

    @Override
    public String getRequestUrl() {
        return null;
    }
}
