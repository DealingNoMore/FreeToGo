package com.softegg.freetogo.User.service;

import com.softegg.freetogo.User.bean.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:数据库操作抽象接口类
 * @author:zhanglinhaon
 * @date:2024/5/7 16:06
 */
@Service
public interface UsersService {
    List<Users> findAll();//查找所有用户

    void add(Users user);//以user为用户入库

    void deleteById(int id);//删除对应id用户

    Users getUserById(int id);//获得对应id用户

    void update(Users user);//更新用户信息

    boolean isRegister(String phone);//判断该手机号（账号）是否已经入库

    Users getUserByPhone(String phone);//根据手机号获得用户

    boolean isGuide(String phone);//查询是否是导游

    int getIdByPhone(String phone);//查找uid
    void showInfo(Users user);//打印所有信息
}
