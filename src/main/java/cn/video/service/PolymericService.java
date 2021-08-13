package cn.video.service;

import cn.video.vo.ParseVO;

import java.io.IOException;

public interface PolymericService {

    ParseVO parseUrl(String url) throws IOException;
}
