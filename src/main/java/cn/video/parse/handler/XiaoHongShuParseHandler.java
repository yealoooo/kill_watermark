package cn.video.parse.handler;

import cn.video.controller.vo.ParseVO;
import cn.video.parse.Parse;
import cn.video.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class XiaoHongShuParseHandler extends Parse {

    @Override
    public ParseVO parseUrl(String url) {
        Map<String, String> headMap = new HashMap<>();

        String locationUrl = HttpUtil.getLocationUrl(url, headMap);

        String itemId = getItemId(locationUrl);

        System.out.println(itemId);
        String requestUrl = getRequestUrl().replace("${item_id}", itemId);

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
    public String getRequestUrl() {
        return "https://www.xiaohongshu.com/fe_api/burdock/weixin/v2/note/${item_id}/single_feed";
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return super.getHeaderMap();

    }

    private String getXSign(String requestUrl) {
        requestUrl = requestUrl.split(".com")[1] + "WSUDD";
        return "X" + DigestUtils.md5DigestAsHex(requestUrl.getBytes()).toLowerCase();
    }

}
