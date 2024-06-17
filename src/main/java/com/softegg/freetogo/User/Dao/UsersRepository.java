package com.softegg.freetogo.User.Dao;

import com.softegg.freetogo.User.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:继承Jpa数据库接口类
 * @author:zhanglinhao
 * @date:2024/5/8 8:50
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByPhone(String phone);//自定义函数，根据手机号进行查找返回对应对象
}
