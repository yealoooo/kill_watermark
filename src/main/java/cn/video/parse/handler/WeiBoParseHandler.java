package cn.video.parse.handler;

import cn.video.exceptions.BasicException;
import cn.video.parse.Parse;
import cn.video.util.HttpRequestHeaderUtil;
import cn.video.util.HttpUtil;
import cn.video.util.UrlUtil;
import cn.video.vo.ImageVO;
import cn.video.vo.ParseVO;
import cn.video.vo.VideoVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WeiBoParseHandler implements Parse {

    private static final String REQUEST_URL = "https://m.weibo.cn";
    private List<String> WB_DOMAIN = new ArrayList<String>(){{
        add("wx4.sinaimg.cn");
        add("wx2.sinaimg.cn");
        add("wx3.sinaimg.cn");
    }};

    @Override
    public ParseVO parseUrl(String url) throws IOException {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("user-agent", HttpRequestHeaderUtil.getAgent());

        String locationUrl = HttpUtil.getLocationUrl(url, headMap);

        String body = HttpUtil.getBody(REQUEST_URL + locationUrl, headMap);

        if (body.contains("由于作者隐私设置，你没有权限查看此微博")) {
            throw new BasicException(500, "由于作者隐私设置，不可以通过分享查看。(待后续通过其他方法拿到，暂时没时间研究)");
        }

        String bodyData = getData(body);
        if (!StringUtils.hasLength(body)) {
            throw new BasicException(500, "解析失败");
        }

        ParseVO parseVO = new ParseVO();

        JSONObject pageInfoJson = (JSONObject) JSON.parseArray(bodyData).get(0);
        JSONObject statusDataJson = pageInfoJson.getJSONObject("status");


        // 获取视频 如果为空则认为是图片
        JSONObject pageInfoDataJson = statusDataJson.getJSONObject("page_info");
        if (null != pageInfoDataJson) {
            JSONObject mediaInfo = pageInfoDataJson.getJSONObject("media_info");
            if (null != mediaInfo) {
                String videoUrl = mediaInfo.getString("stream_url_hd");
                System.out.println(videoUrl);
                parseVO.setVideoData(new VideoVO(videoUrl, videoUrl, true));
            }
        }

        // 获取图片
        JSONArray picInfoDataJson = statusDataJson.getJSONArray("pics");
        if (null != picInfoDataJson) {
            List<String> picUrlList = picInfoDataJson.stream().map(x -> {
                JSONObject picObj = (JSONObject) x;
                return picObj.getJSONObject("large").getString("url");
            }).collect(Collectors.toList());
            for (String s : picUrlList) {
                System.out.println(s);
            }

            picUrlList = picUrlList.stream().map(x -> {
                if (!WB_DOMAIN.contains(x)) {
                    String domain = UrlUtil.getDomain(x);
                    if (StringUtils.hasLength(domain)) {
                        domain = domain.replace("https://", "").replace("http://", "");

                        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                        int index = threadLocalRandom.nextInt(0, WB_DOMAIN.size() - 1);
                        return x.replace(domain, WB_DOMAIN.get(index));
                    }
                }
                return null;
            }).filter(StringUtils::hasLength).collect(Collectors.toList());
            parseVO.setImageData(new ImageVO(picUrlList, true));
        }

        return parseVO;
    }

    @Override
    public String getItemId(String url) {
        return null;
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return null;
    }

    private String getData(String body) {
        String repx = "<script>(((\\n).*)*)[^<script>]</script>";
        Pattern p = Pattern.compile(repx);
        Matcher m = p.matcher(body);
        if (m.find()) {
            String group = m.group(0);
            int start = group.indexOf("[{");
            int end = group.lastIndexOf("}]");
            return group.substring(start, end + 2);
        }

        return null;
    }

}
