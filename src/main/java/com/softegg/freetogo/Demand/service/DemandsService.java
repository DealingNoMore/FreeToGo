package com.softegg.freetogo.Demand.service;

import com.softegg.freetogo.Demand.bean.Demands;

import java.util.List;

/**
 * @description:数据库操作抽象接口类
 * @author:wuyifan
 * @date:2024/5/10 20：59
 */
public interface DemandsService {
    List<Demands> findAll();//查找所有需求

    void add(Demands demand);//以demand为用户入库

    void deleteById(int id);//删除对应id需求

    Demands getDemandById(int id);//获得对应id需求

    void update(Demands demand);//更新需求信息

    List<Demands>  getDemandsByPhone(String phone);//根据手机号获得对应游客用户的服务

    List<Demands>  getDemandsByCity(String city);//根据手机号获得对应游客用户的服务

    List<Demands>  getDemandsByProvince(String province);//根据手机号获得对应游客用户的服务
}
