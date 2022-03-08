package cn.video.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.video.entity.ProxyIpEntity;
import cn.video.mapper.ProxyIpMapper;

public class ReplaceIpUtil {

    private static final HttpRequest httpRequest = HttpRequest
            .get("http://http.tiqu.letecs.com/getip3?num=1&type=1&pro=&city=0&yys=0&port=11&time=4&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=&gm=4");

    public static void replaceProxyIp() {
        String ipAndPort;
        while (true) {
            String body = request();
            if (!body.contains("code")) {
                ipAndPort = body.trim();
                break;
            }
        }

        String[] split = ipAndPort.split(":");

        ProxyIpMapper proxyIpMapper = SpringUtil.getBean(ProxyIpMapper.class);
        ProxyIpEntity proxyIpEntity = proxyIpMapper.selectById(1);
        if (null == proxyIpEntity) {
            proxyIpEntity = new ProxyIpEntity();
            proxyIpEntity.setId(1L);
        }
        proxyIpEntity.setIp(split[0]);
        proxyIpEntity.setPort(Integer.parseInt(split[1]));
        proxyIpMapper.updateById(proxyIpEntity);
    }

    private static String request() {
        return httpRequest.execute().body();
    }
}
