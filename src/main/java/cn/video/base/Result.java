package cn.video.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;
    private Object data;


    public static Result success(Object data) {
        return new Result(200, data);
    }
}
