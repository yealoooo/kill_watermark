package cn.video.controller;

import cn.hutool.http.HttpRequest;
import cn.video.base.Result;
import cn.video.service.PolymericService;
import cn.video.util.UrlUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/polymeric")
public class PolymericController {

    @Autowired
    private PolymericService polymericService;

    @GetMapping("/parseUrl")
    public Result parseUrl(@RequestParam String url) throws IOException {

//        if (url.contains("xhslink.com")) {
//            HttpRequest httpRequest = HttpRequest.get("http://pc93fh.natappfree.cc/polymeric/parseUrl?url=" + url);
//
//            String body = httpRequest.execute().body();
//            return Result.aes(JSON.parseObject(body).getString("data"));
//        }
        return Result.success(this.polymericService.parseUrl(UrlUtil.filterUrl(url)));
    }


    @GetMapping("/downLoadVideo")
    public void downLoadVideo(@RequestParam String url, HttpServletResponse response) {
        this.polymericService.downLoadVideo(url, response);
    }

    @GetMapping("/downLoadImage")
    public void downLoadImage(@RequestParam String url, HttpServletResponse response) {
        this.polymericService.downLoadImage(url, response);
    }

    @GetMapping("/fankui")
    public Result fankui(@RequestParam String fk) {
        this.polymericService.fankui(fk);
        return Result.success(null);
    }

    @GetMapping("/getTitleText")
    public Result getTitleText() {
        String titleText = "将复制好的连接粘贴至下方点击解析即可!\n" +
                "目前支持(抖音,皮皮虾,小红书,微博)\n" +
                "注:(小红书暂且仅支持 图片解析)\n" +
                "(皮皮虾暂且支持视频解析)";

        return Result.success(titleText);
    }

    @PostMapping("/testPost")
    public Object tsetPost(@RequestBody Map<String, Object> data) {
        return JSON.toJSONString(data);
    }


}
