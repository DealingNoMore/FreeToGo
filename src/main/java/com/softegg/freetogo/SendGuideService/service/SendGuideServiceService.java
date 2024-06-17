package com.softegg.freetogo.SendGuideService.service;

import org.springframework.stereotype.Service;

/**
 * @description:抽象需求发送服务接口类
 * @author:wuyifan
 * @date:2024/5/13 11:11
 */
@Service
public interface SendGuideServiceService {

    int sendnewGuideServiceAccount(String city, String phone, String date, String message);
}
