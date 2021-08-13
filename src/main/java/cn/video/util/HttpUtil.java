package cn.video.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpUtil {

    private static Response get(String url, Map<String, String> headerMap) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        if (null != headerMap && !headerMap.isEmpty()) {
            for (String header : headerMap.keySet()) {
                requestBuilder.header(header, headerMap.get(header));
            }
        }

        Request request = requestBuilder.build();

        OkHttpClient okHttpClient = new OkHttpClient();

        return okHttpClient.newCall(request).execute();
    }


    public static String getBody(String url, Map<String, String> header) throws IOException {
        return Objects.requireNonNull(get(url, header).body()).string();
    }

    public static String getLocationUrl(String url, Map<String, String> header) throws IOException {
        return get(url, header).request().url().toString();
    }
}
