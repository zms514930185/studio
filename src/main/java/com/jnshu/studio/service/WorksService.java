package com.jnshu.studio.service;

import com.jnshu.studio.model.Works;

import java.util.List;

public interface WorksService {

    List<Works> selectWorks(Works works);

    int deleteByPrimaryKey(long id);

    long insertSelective(Works works);

    int updateByPrimaryKeySelective(Works works);
}
