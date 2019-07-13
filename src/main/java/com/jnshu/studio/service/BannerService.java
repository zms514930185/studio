package com.jnshu.studio.service;


import com.jnshu.studio.model.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> selectBanner(Banner banner);

    int deleteByPrimaryKey(long id);

    long insertSelective(Banner banner);

    int updateByPrimaryKeySelective(Banner banner);
}
