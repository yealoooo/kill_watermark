package cn.video.exceptions;


import cn.video.base.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = BasicException.class)
    public Result doBasicsException(BasicException exception) {
        return Result.error(exception.getCode(), exception.getMessage());
    }
}
