package cn.video.util;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpUtil {

    private static HttpResponse get(String url, Map<String, String> headerMap){

        HttpRequest httpRequest = HttpRequest.get(url);

        if (null != headerMap && !headerMap.isEmpty()) {
            for (String header : headerMap.keySet()) {
                httpRequest.header(header, headerMap.get(header));
            }
        }

        return httpRequest.execute();
    }


    public static String getBody(String url, Map<String, String> header) {
//        System.out.println("getBody:\n" + url + "\n" + JSON.toJSONString(header));
        return get(url, header).body();
    }

    public static String getLocationUrl(String url, Map<String, String> header) {
//        System.out.println("getLocationUrl:\n" + url + "\n" + JSON.toJSONString(header));
        HttpResponse httpResponse = get(url, header);
        return httpResponse.headers().get(Header.LOCATION.getValue()).get(0);
    }
}
