package com.jnshu.studio.service;

import com.jnshu.studio.model.Navigation;

import java.util.List;

public interface NavigationService {
    int deleteByPrimaryKey(Long id);

    int insert(Navigation record);

    int insertSelective(Navigation record);

    Navigation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);

    List<Navigation> selectNavigationAll();

    List<Navigation> selectNavigation(Navigation navigation);
}
