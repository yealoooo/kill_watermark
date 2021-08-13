package cn.video.controller;

import cn.video.base.Result;
import cn.video.service.PolymericService;
import cn.video.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/polymeric")
public class PolymericController {

    @Autowired
    private PolymericService polymericService;

    @GetMapping("/parseUrl")
    public Result parseUrl(@RequestParam String url) throws IOException {
        return Result.success(this.polymericService.parseUrl(UrlUtil.filterUrl(url)));
    }
}
