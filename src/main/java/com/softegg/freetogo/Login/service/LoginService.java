package com.softegg.freetogo.Login.service;

import org.springframework.stereotype.Service;

/**
 * @description:抽象登录服务接口类
 * @author:zhanglinhao
 * @date:2024/5/9 8:37
 */
@Service
public interface LoginService {
    int loginAccount(String phone, String password);//登录
//    int registerAccount(String phone, String password);

    int registerAccount(String name,
                        String password,
                        String phone,
                        String nickname,
                        String IDCard);//注册

}
