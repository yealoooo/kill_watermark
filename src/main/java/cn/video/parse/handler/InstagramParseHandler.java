package cn.video.parse.handler;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.ssl.TrustAnyHostnameVerifier;
import cn.video.controller.vo.ParseVO;
import cn.video.parse.Parse;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class InstagramParseHandler extends Parse {
    @Override
    public ParseVO parseUrl(String url) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("cookie", "mid=YelTygALAAGvUtdijgzbocAHd9vF; ig_did=32478FBA-FF39-402A-AA4F-CB40E8AAC55B; ig_nrcb=1; csrftoken=chgXmyJiIE6WUIhIf65070zb2CkocKhX; ds_user_id=51284859450; sessionid=51284859450%3A2NceKf0ErK4XeI%3A2; rur=\"EAG\\05451284859450\\0541674217559:01f783fcf06acae15ffb6fb9387582accbaf37f99bcb1b4d781d31eaf18580fc5a1eb932\"");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36 Edg/97.0.1072.62");
        headerMap.put("host", "www.instagram.com");
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        headerMap.put("sec-ch-ua-platform", "\"Windows\"");
        headerMap.put("sec-fetch-dest", "document");
        headerMap.put("sec-fetch-mode", "navigate");
        headerMap.put("sec-fetch-user", "?1");
        headerMap.put("upgrade-insecure-requests", "1");
        headerMap.put("authority", "www.instagram.com");

        String data = doGet(url, headerMap);

//
//        HttpRequest httpRequest = HttpUtil.createGet(url);
//        httpRequest.addHeaders(headerMap);
//        httpRequest.setHttpProxy("127.0.0.1", 7890);

        System.out.println(data);
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

    public static void main(String[] args) throws IOException {
        String url = "https://www.instagram.com/p/CYtXumNPmX3/?utm_source=ig_web_copy_link";

        InstagramParseHandler instagramParseHandler = new InstagramParseHandler();
        ParseVO parseVO = instagramParseHandler.parseUrl(url);
    }

    public static String doGet(String httpurl, Map<String, String> headerMap) {
        HttpsURLConnection  connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {

            SSLContext sc = SSLContext.getInstance("SSL");
            // 创建远程url连接对象
            URL url = new URL(httpurl);

            Proxy proxy1=new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
            //设置代理
            connection = (HttpsURLConnection) url.openConnection(proxy1);

//            connection.setSSLSocketFactory(sc.getSocketFactory());
//            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());

            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
//            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);

            for (String s : headerMap.keySet()) {
                connection.setRequestProperty(s, headerMap.get(s));
            }
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                GZIPInputStream gunzip = new GZIPInputStream(is);
                byte[] dataBytes = new byte[1024];
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                int read = 0;
                while ((read = gunzip.read(dataBytes)) != -1) {
                    sbf.append(new String(dataBytes, 0, read, StandardCharsets.UTF_8));
                }
                return sbf.toString();
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            assert connection != null;
            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
}
