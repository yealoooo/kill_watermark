package cn.video.parse;

import cn.video.vo.ParseVO;

import java.io.IOException;

public interface Parse {

    ParseVO parseUrl(String url) throws IOException;

}
