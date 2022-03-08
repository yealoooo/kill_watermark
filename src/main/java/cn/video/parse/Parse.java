package cn.video.parse;

import cn.video.controller.vo.ParseVO;
import cn.video.entity.HandlerCategoryEntity;
import cn.video.entity.RequestHeaderEntity;
import cn.video.repository.HandlerCategoryRepository;
import cn.video.repository.RequestHeaderRepository;
import cn.video.util.ApplicationContextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Parse {

    public abstract ParseVO parseUrl(String url) throws Exception;

    public abstract String getItemId(String url);

    public abstract String getRequestUrl();

    public Map<String, String> getHeaderMap() {
        String handlerBeanName = this.getClass().getSimpleName();
        handlerBeanName = handlerBeanName.substring(0, 1).toLowerCase() + handlerBeanName.substring(1);

        HandlerCategoryRepository handlerCategoryRepository = ApplicationContextUtils.getBean(HandlerCategoryRepository.class);
        RequestHeaderRepository requestHeaderRepository = ApplicationContextUtils.getBean(RequestHeaderRepository.class);

        HandlerCategoryEntity handlerCategoryEntity = handlerCategoryRepository.getByHandlerBeanName(handlerBeanName);
        List<RequestHeaderEntity> requestHeaderEntityList = requestHeaderRepository.selectByHandlerCategoryId(handlerCategoryEntity.getId());

        Map<String, String> headerMap = new HashMap<>();
        requestHeaderEntityList.forEach(requestHeaderEntity -> {
            headerMap.put(requestHeaderEntity.getHead(), requestHeaderEntity.getValue());
        });
        return headerMap;
    }
}
