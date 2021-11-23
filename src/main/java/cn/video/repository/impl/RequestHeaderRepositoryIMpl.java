package cn.video.repository.impl;

import cn.video.entity.RequestHeaderEntity;
import cn.video.mapper.RequestHeaderMapper;
import cn.video.repository.RequestHeaderRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestHeaderRepositoryIMpl implements RequestHeaderRepository {

    private final RequestHeaderMapper requestHeaderMapper;

    public RequestHeaderRepositoryIMpl(RequestHeaderMapper requestHeaderMapper) {
        this.requestHeaderMapper = requestHeaderMapper;
    }

    @Override
    public List<RequestHeaderEntity> selectByHandlerCategoryId(Long handlerCategoryId) {
        return requestHeaderMapper.selectList(new QueryWrapper<RequestHeaderEntity>().lambda().eq(RequestHeaderEntity::getHandlerCategoryId, handlerCategoryId));
    }
}
