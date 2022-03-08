package cn.video.config;

import cn.video.base.ParseHandlerCache;
import cn.video.entity.HandlerCategoryEntity;
import cn.video.mapper.HandlerCategoryMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationContextEventListener implements ApplicationListener<ApplicationContextEvent> {
    private final HandlerCategoryMapper handlerCategoryMapper;

    public ApplicationContextEventListener(HandlerCategoryMapper handlerCategoryMapper) {
        this.handlerCategoryMapper = handlerCategoryMapper;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        List<HandlerCategoryEntity> handlerCategoryEntityList = handlerCategoryMapper.selectList(null);

        for (HandlerCategoryEntity handlerCategory : handlerCategoryEntityList) {
            ParseHandlerCache.setParseHandler(handlerCategory.getDomain(), handlerCategory.getHandlerBeanName());
        }
    }
}
