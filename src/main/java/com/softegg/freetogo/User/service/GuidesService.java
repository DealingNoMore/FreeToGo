package com.softegg.freetogo.User.service;

import com.softegg.freetogo.User.bean.Guides;
import org.springframework.stereotype.Service;

/**
 * @description: 数据库操作抽象接口类
 * @author: zhanglinhao
 * @date: 2024/5/16 10:45
 */
@Service
public interface GuidesService {
    boolean registerToGuide(Guides guide);//注册成为导游，将导游注册信息入库
}
