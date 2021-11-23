package cn.video.base;

import cn.video.util.AESUtil;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result {

    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {

        return new Result(200, "success", AESUtil.encryptAES(JSON.toJSONString(data)));
    }

    public static Result success(String data) {

        return new Result(200, "success", AESUtil.encryptAES(data));
    }


    public static Result aes(String aesData) {

        return new Result(200, "success", aesData);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg, null);
    }
}
