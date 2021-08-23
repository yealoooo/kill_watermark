package cn.video.service.impl;

import cn.hutool.http.HttpRequest;
import cn.video.exceptions.BasicException;
import cn.video.parse.Parse;
import cn.video.parse.ParseContext;
import cn.video.parse.handler.DouYinParseHandler;
import cn.video.service.PolymericService;
import cn.video.util.HttpRequestHeaderUtil;
import cn.video.vo.ParseVO;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class PolymericServiceImpl implements PolymericService {

    @Override
    public ParseVO parseUrl(String url) {
        Parse parser = ParseContext.getParser(url);
        if (null == parser) throw new BasicException(500, "解析失败");
        try {
            return parser.parseUrl(url);
        } catch (IOException e) {
            throw new BasicException(500, "解析失败");
        }
    }

    @Override
    public void downLoadVideo(String url, HttpServletResponse response) {
        // 1.下载网络文件
        download(url, response);
        response.setHeader("Content-Type", "video/mp4");
    }

    @Override
    public void downLoadImage(String url, HttpServletResponse response) {
        download(url, response);
    }

    @Override
    public void fankui(String fk) {
        System.out.println("反馈内容:" + fk);
    }

    private void download(String url, HttpServletResponse response) {
        int byteRead;
        try {
            String agent = HttpRequestHeaderUtil.getAgent();
            InputStream inputStream = HttpRequest.get(url).header("user-agent", agent).execute().bodyStream();
            //3.写入文件
            ServletOutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[2048];
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BasicException(500, "导出失败");
        }
    }

}
