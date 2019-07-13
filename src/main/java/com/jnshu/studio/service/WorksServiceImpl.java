package com.jnshu.studio.service;

import com.jnshu.studio.mapper.WorksMapper;
import com.jnshu.studio.model.Works;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorksServiceImpl implements WorksService {

    @Resource
    WorksMapper worksMapper;


    @Override
    public List<Works> selectWorks(Works works) {
        return worksMapper.selectWorks(works);
    }

    @Override
    public int deleteByPrimaryKey(long id) {
        return worksMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long insertSelective(Works works) {
        return worksMapper.insertSelective(works);
    }

    @Override
    public int updateByPrimaryKeySelective(Works works) {
        return worksMapper.updateByPrimaryKeySelective(works);
    }
}
