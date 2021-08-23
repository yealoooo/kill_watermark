package cn.video.parse;

import cn.video.vo.ParseVO;

import java.io.IOException;
import java.util.Map;

public interface Parse {

    ParseVO parseUrl(String url) throws IOException;

    String getItemId(String url);

    Map<String, String> getHeaderMap();
}
