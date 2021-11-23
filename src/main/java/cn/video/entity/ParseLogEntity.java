package cn.video.entity;

import cn.video.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ParseLogEntity {


    public static void main(String[] args) {
        System.out.println(HttpUtil.getBody("https://twitter.com/starlordsweeny/status/1430048665364664322?s=21", null));
    }
}
