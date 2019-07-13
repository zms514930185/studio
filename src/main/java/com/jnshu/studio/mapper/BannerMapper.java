package com.jnshu.studio.mapper;

import com.jnshu.studio.model.Banner;

import java.util.List;

/**
 * BannerMapper banner图
 * @author 字决
 * @date 2019/07/01
 */
public interface BannerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

    List<Banner> selectBanner(Banner banner);
}