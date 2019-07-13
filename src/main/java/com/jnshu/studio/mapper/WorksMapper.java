package com.jnshu.studio.mapper;

import com.jnshu.studio.model.Works;

import java.util.List;

public interface WorksMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Works record);

    long insertSelective(Works record);

    Works selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Works record);

    int updateByPrimaryKeyWithBLOBs(Works record);

    int updateByPrimaryKey(Works record);

    List<Works> selectWorks(Works works);
}