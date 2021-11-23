package cn.video.repository.impl;

import cn.video.entity.HandlerCategoryEntity;
import cn.video.mapper.HandlerCategoryMapper;
import cn.video.repository.HandlerCategoryRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Repository;

@Repository
public class HandlerCategoryRepositoryImpl implements HandlerCategoryRepository {

    private final HandlerCategoryMapper handlerCategoryMapper;

    public HandlerCategoryRepositoryImpl(HandlerCategoryMapper handlerCategoryMapper) {
        this.handlerCategoryMapper = handlerCategoryMapper;
    }

    @Override
    public HandlerCategoryEntity getByHandlerBeanName(String handlerBeanName) {
        return handlerCategoryMapper.selectOne(new QueryWrapper<HandlerCategoryEntity>().lambda().eq(HandlerCategoryEntity::getHandlerBeanName, handlerBeanName));
    }
}
