package cn.video.config;

import cn.video.base.ParseHandlerCache;
import cn.video.entity.HandlerCategoryEntity;
import cn.video.mapper.HandlerCategoryMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@DependsOn("applicationContextUtils")
public class LoadParseHandlerOnPostConstruct {

    private final HandlerCategoryMapper handlerCategoryMapper;

    public LoadParseHandlerOnPostConstruct(HandlerCategoryMapper handlerCategoryMapper) {
        this.handlerCategoryMapper = handlerCategoryMapper;
    }

    @PostConstruct
    public void loadParseHandler() {
        List<HandlerCategoryEntity> handlerCategoryEntityList = handlerCategoryMapper.selectList(null);

        for (HandlerCategoryEntity handlerCategory : handlerCategoryEntityList) {
            ParseHandlerCache.setParseHandler(handlerCategory.getDomain(), handlerCategory.getHandlerBeanName());
        }
    }
}
