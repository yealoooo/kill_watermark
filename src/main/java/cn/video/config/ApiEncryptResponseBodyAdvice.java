package cn.video.config;

import cn.video.annotation.ApiEncryptAnnotation;
import cn.video.base.Result;
import cn.video.util.AESUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//声明该类要处理的包路径
@ControllerAdvice("cn.video.controller")
public class ApiEncryptResponseBodyAdvice implements ResponseBodyAdvice<Result<Object>> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(ApiEncryptAnnotation.class);
    }

    @Override
    public Result<Object> beforeBodyWrite(Result<Object> result, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (result == null) return null;

        Object data = result.getData();

        String encryptData;
        if (data instanceof String) {
            encryptData = AESUtil.encryptAES((String) data);
        } else {
            encryptData = AESUtil.encryptAES(JSON.toJSONString(data));
        }

        result.setData(encryptData);

        return result;
    }
}
