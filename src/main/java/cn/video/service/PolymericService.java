package cn.video.service;

import cn.video.controller.vo.ParseVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PolymericService {

    ParseVO parseUrl(String url);

    void downLoadVideo(String url, HttpServletResponse response);

    void downLoadImage(String url, HttpServletResponse response);

}
