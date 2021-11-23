package cn.video.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static<T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    public static<T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
