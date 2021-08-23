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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XiaoHongShuParseHandler implements Parse {

    private static final String INFO_URL = "https://www.xiaohongshu.com/fe_api/burdock/weixin/v2/note/${item_id}/single_feed";

    @Override
    public ParseVO parseUrl(String url) throws IOException {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("user-agent", HttpRequestHeaderUtil.getAgent());

        String locationUrl = HttpUtil.getLocationUrl(url, headMap);

        String itemId = getItemId(locationUrl);

        System.out.println(itemId);
        String requestUrl = INFO_URL.replace("${item_id}", itemId);

        headMap.put("x-sign", getXSign(requestUrl));
        headMap.putAll(getHeaderMap());

        String body = HttpUtil.getBody(requestUrl, headMap);

        System.out.println("小红书解析:" + body);

        JSONObject jsonObject = JSON.parseObject(body);

        List<String> imageUrlList = jsonObject.getJSONObject("data").getJSONArray("imageList").stream().map(x -> {
            JSONObject imgObj = (JSONObject) x;
            return imgObj.getString("url").replace("http://", "https://");
        }).collect(Collectors.toList());

        return ParseVO.image(imageUrlList, true);
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
            put("authorization", "wxmp.36f11a45-51c8-402a-910d-0cdad75eafda");
            put("device-fingerprint", "WHJMrwNw1k/GZQYJgbVP1Int3Fky81ey44AF34mZOUHcsAVFqScxl595rnisZ+35k35qp9B6ZumLJYUtqXNVQIjlh2oWHn6cLdCW1tldyDzmauSxIJm5Txg==1487582755342");
            put("content-type", "application/json");
            put("referer", "https://servicewechat.com/wxb296433268a1c654/46/page-frame.html");
            put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
            put("accept-encoding", "gzip, deflate, br");
        }};

    }

    private String getXSign(String requestUrl) {
        requestUrl = requestUrl.split(".com")[1] + "WSUDD";
        return "X" + DigestUtils.md5DigestAsHex(requestUrl.getBytes()).toLowerCase();
    }

}
