package com.jnshu.studio.service;

import com.jnshu.studio.mapper.NavigationMapper;
import com.jnshu.studio.model.Navigation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NavigationServiceImpl implements NavigationService{
    @Resource
    NavigationMapper navigationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return navigationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Navigation navigation) {
        navigation.setStatus(10);
        navigation.setCreateAt(System.currentTimeMillis());
        navigation.setUpdateAt(System.currentTimeMillis());
        navigation.setUpdateBy("字决");
        navigation.setCreateBy("字决");
        return navigationMapper.insert(navigation);
    }

    @Override
    public int insertSelective(Navigation record) {
        return 0;
    }

    @Override
    public Navigation selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Navigation record) {
        return navigationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Navigation record) {
        return 0;
    }

    @Override
    public List<Navigation> selectNavigationAll() {
        return navigationMapper.selectNavigationAll();
    }

    @Override
    public List<Navigation> selectNavigation(Navigation navigation) {
        return navigationMapper.selectNavigation(navigation);
    }
}
