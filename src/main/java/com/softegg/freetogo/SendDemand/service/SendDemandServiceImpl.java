package com.softegg.freetogo.SendDemand.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.Demand.service.DemandsService;
import com.softegg.freetogo.User.bean.Users;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @description:需求服务实现类
 * @author:wuyifan
 * @date:2024/5/10 23:10
 */
@Component
public class SendDemandServiceImpl implements SendDemandService {
    @Autowired
    DemandsService demandsService;
    @Autowired
    UsersService usersService;

    /**
     * @description: 导游服务实现函数，根据返回码进行操作
     * @param: city
     * @return: int
     * @author: wuyifan
     * @date: 2024/5/10 23:25
     */

    @Override
    public int sendnewDemandAccount(String city, String phone, String date, String message) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        //对获取的date信息进行处理
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter1);
        date = date.replace("[", "");
        date = date.replace("]", "");
        String[] parts = date.split(",");
        String dDate = parts[0];
        String eDate = parts[1];
        //获取整个导游服务的总天数
        eDate = eDate.trim();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dDate, formatter2);
        LocalDate endDate = LocalDate.parse(eDate, formatter2);
        String departureTime = startDate.format(formatter2);
        String endTime = endDate.format(formatter2);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //根据收到的消息，初始化游客需求内容
        Demands demand = new Demands();
        Users user = usersService.getUserByPhone(phone);
        demand.setPhone(phone);
        demand.setCity(city);
        demand.setTouristGender(user.isGender());
        demand.setNickname(user.getNickname());
        demand.setCreateTime(formattedDateTime);
        demand.setDepartureDate(departureTime);
        demand.setEndDate(endTime);
        demand.setSumDay(String.valueOf(daysBetween));
        demand.setMessage(message);
        demand.setStatus(0);
        demandsService.add(demand);
        return 1005;//添加游客需求成功
    }
}
