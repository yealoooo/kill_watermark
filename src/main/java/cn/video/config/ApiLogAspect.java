package cn.video.config;

import cn.hutool.core.date.DateUtil;
import cn.video.entity.ParseLogEntity;
import cn.video.mapper.ParseLogEntityMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class ApiLogAspect {

    @Autowired
    private ParseLogEntityMapper parseLogEntityMapper;

    private static final ThreadLocal<ParseLogEntity> sysLogThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Long> inTimeThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(public * cn.video.controller.*.*(..))")
    public void apiLog() {
    }

    @AfterThrowing(throwing = "ex", pointcut = "apiLog()")
    public void throwss(Throwable ex) {
        ParseLogEntity parseLogEntity = sysLogThreadLocal.get();
        parseLogEntity.setResponseCode(500L);

        log.error("errorMessage:{}", ex.getMessage());
        parseLogEntity.setResponseMessage(ex.getMessage());

        long time = getTime();
        log.info("time:{}", time);
        parseLogEntity.setUseTime(time);

        // 保存至数据库
        saveToDB();
    }

    @AfterReturning(pointcut = "apiLog()", returning = "result")
    public void afterReturning(Object result) {
        // 数据返回时 保存
        ParseLogEntity sysLogEntity = sysLogThreadLocal.get();

        sysLogEntity.setResponseCode(200L);

        //用时
        long time = getTime();
        sysLogEntity.setUseTime(time);
        log.info("time:{}", time);

        // 结果
        if (null != result) {
            String resultJson = JSON.toJSONString(result);
            sysLogEntity.setParseData(resultJson);
            log.info("result:{}", resultJson);
        }

        // 保存至 数据库
        saveToDB();
    }

    @Before("apiLog()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        String logId = UUID.randomUUID().toString().replace("-", "");
        log.info("logId:{}", logId);

        // 进入时间
        setBeforeTime();

        // 前置信息数据
        ParseLogEntity parseLogEntity = new ParseLogEntity();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        parseLogEntity.setApi(request.getRequestURI());

        //请求的参数
        try {
            Object[] args = joinPoint.getArgs();

            Map<String, Object> paramMap = new HashMap<>();

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    paramMap.put(parameter.getName(), args[i]);
                } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                    parseLogEntity.setRequestData(JSON.toJSONString(args[i])); // body 只有一个直接插入
                }
            }

            parseLogEntity.setRequestData(paramMap.isEmpty() ? null : JSON.toJSONString(paramMap));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //时间
        parseLogEntity.setRequestDate(DateUtil.date().toTimestamp());

        // 接口类型
        sysLogThreadLocal.set(parseLogEntity);
    }


    private void setBeforeTime() {
        long beginTime = System.currentTimeMillis();
        inTimeThreadLocal.set(beginTime);
    }

    private long getTime() {
        Long beginTime = inTimeThreadLocal.get();
        return System.currentTimeMillis() - beginTime;
    }

    private void saveToDB() {
        try {
            ParseLogEntity parseLogEntity = sysLogThreadLocal.get();
            parseLogEntityMapper.insert(parseLogEntity);
        } finally {
            inTimeThreadLocal.remove();
            sysLogThreadLocal.remove();
        }

    }
}
