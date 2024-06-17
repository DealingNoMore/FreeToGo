package com.softegg.freetogo.GuideService.service;

import com.softegg.freetogo.GuideService.bean.GuideService;

import java.util.List;

/**
 * @description:数据库操作抽象接口类
 * @author:wuyifan
 * @date:2024/5/10 20：59
 */
public interface GuideServiceService {
    List<GuideService> findAll();//查找所有需求

    void add(GuideService guideService);//以demand为用户入库

    void deleteById(int id);//删除对应id需求

    GuideService getGuideServiceById(int id);//获得对应id需求

    void update(GuideService guideService);//更新需求信息

    List<GuideService> getGuideServiceByPhone(String phone);//获得对应手机号的导游用户的所有服务

    List<GuideService>  getGuideServiceByCity(String city);//根据所选城市获得导游服务列表

    List<GuideService>  getGuideServiceByProvince(String province);//根据所选城市获得导游服务列表
}
