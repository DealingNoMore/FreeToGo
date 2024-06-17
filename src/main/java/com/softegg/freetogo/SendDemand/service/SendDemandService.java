package com.softegg.freetogo.SendDemand.service;

import org.springframework.stereotype.Service;

/**
 * @description:抽象需求发送服务接口类
 * @author:wuyifan
 * @date:2024/5/10 23:31
 */
@Service
public interface SendDemandService {
    int sendnewDemandAccount(String city, String phone, String date, String message);
}
