package cn.video.repository;

import cn.video.entity.RequestHeaderEntity;

import java.util.List;

public interface RequestHeaderRepository {

    List<RequestHeaderEntity> selectByHandlerCategoryId(Long handlerCategoryId);
}
