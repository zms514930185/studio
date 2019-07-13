package com.jnshu.studio.service;

import com.jnshu.studio.mapper.BannerMapper;
import com.jnshu.studio.model.Banner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    BannerMapper bannerMapper;

    @Override
    public List<Banner> selectBanner(Banner banner) {
        return bannerMapper.selectBanner(banner);
    }

    @Override
    public int deleteByPrimaryKey(long id) {
        return bannerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long insertSelective(Banner banner) {
        return bannerMapper.insertSelective(banner);
    }

    @Override
    public int updateByPrimaryKeySelective(Banner banner) {
        return bannerMapper.updateByPrimaryKeySelective(banner);
    }
}
