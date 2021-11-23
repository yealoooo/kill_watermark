package cn.video.util;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.video.entity.ProxyIpEntity;
import cn.video.mapper.ProxyIpMapper;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

public class HttpUtil {

    private static HttpResponse get(String url, Map<String, String> headerMap){
        HttpRequest httpRequest = HttpRequest.get(url);

        if (null != headerMap && !headerMap.isEmpty()) {
            for (String header : headerMap.keySet()) {
                httpRequest.header(header, headerMap.get(header));
            }
        }

        if (null == headerMap || !headerMap.containsKey("user-agent")) {
            // 随机user-agent
            httpRequest.header("user-agent", HttpRequestHeaderUtil.getAgent());
        }

        ProxyIpMapper proxyIpMapper = ApplicationContextUtils.getBean(ProxyIpMapper.class);
        ProxyIpEntity proxyIpEntity = proxyIpMapper.selectById(1);

        Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyIpEntity.getIp(), proxyIpEntity.getPort()));
        return httpRequest.setProxy(proxy).execute();
    }


    public static String getBody(String url, Map<String, String> header) {
        return get(url, header).body();
    }

    public static String getLocationUrl(String url, Map<String, String> header) {
        HttpResponse httpResponse = get(url, header);
        return httpResponse.headers().get(Header.LOCATION.getValue()).get(0);
    }
}
