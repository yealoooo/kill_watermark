package cn.video.repository;

import cn.video.entity.HandlerCategoryEntity;

public interface HandlerCategoryRepository {

    HandlerCategoryEntity getByHandlerBeanName(String handlerBeanName);
}
