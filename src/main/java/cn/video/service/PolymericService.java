package cn.video.service;

import cn.video.vo.ParseVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PolymericService {

    ParseVO parseUrl(String url) throws IOException;

    void downLoadVideo(String url, HttpServletResponse response);

    void downLoadImage(String url, HttpServletResponse response);

    void fankui(String fk);
}
