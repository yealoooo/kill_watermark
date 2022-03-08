package cn.video.util;


import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.video.entity.ProxyIpEntity;
import cn.video.mapper.ProxyIpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static HttpResponse get(String url, Map<String, String> headerMap, boolean proxyFlag) {
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.timeout(100000);

        if (null != headerMap && !headerMap.isEmpty()) {
            for (String header : headerMap.keySet()) {
                httpRequest.header(header, headerMap.get(header));
            }
        }

        if (null == headerMap || !headerMap.containsKey("user-agent")) {
            // 随机user-agent
            httpRequest.header("user-agent", HttpRequestHeaderUtil.getAgent());
        }


        while (true) {
            try {
                if (proxyFlag) {
                    ProxyIpMapper proxyIpMapper = ApplicationContextUtils.getBean(ProxyIpMapper.class);
                    ProxyIpEntity proxyIpEntity = proxyIpMapper.selectById(1);
                    if (null != proxyIpEntity) {
                        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIpEntity.getIp(), proxyIpEntity.getPort()));
                        httpRequest.setProxy(proxy);
                    }
                }
                return httpRequest.execute();
            } catch (IORuntimeException ex) {
                ex.printStackTrace();
                ReplaceIpUtil.replaceProxyIp();
            }
        }
    }


    public static String getBody(String url, Map<String, String> header) {
        return get(url, header, false).body();
    }

    public static String getLocationUrl(String url, Map<String, String> header) {
        HttpResponse httpResponse = get(url, header, false);
        return httpResponse.headers().get(Header.LOCATION.getValue()).get(0);
    }
}
