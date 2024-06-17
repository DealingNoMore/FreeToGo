package com.softegg.freetogo.SendGuideService.service;

import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
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
 * @date:2024/5/13 10:53
 */
@Component
public class SendGuideServiceServiceImpl implements SendGuideServiceService {
    @Autowired
    GuideServiceService guideServiceService;
    @Autowired
    UsersService usersService;

    /**
     * @description: 注册接口实现函数，根据返回码进行操作
     * @param: city
     * @return: int
     * @author: wuyifan
     * @date: 2024/5/10 23:25
     */
    @Override
    public int sendnewGuideServiceAccount(String city, String phone, String date, String message) {
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
        System.out.println(dDate);
        System.out.println(eDate);
        dDate = dDate.trim();
        eDate = eDate.trim();
        dDate = dDate.substring(0, 10);
        eDate = eDate.substring(0, 10);
        System.out.println(dDate);
        System.out.println(eDate);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dDate, formatter2);
        LocalDate endDate = LocalDate.parse(eDate, formatter2);
        String departureTime = startDate.format(formatter2);
        String endTime = endDate.format(formatter2);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //根据收到的消息，初始化导游服务内容
        GuideService guideService = new GuideService();
        Users user = usersService.getUserByPhone(phone);
        guideService.setPhone(phone);
        guideService.setCity(city);
        guideService.setGuideGender(user.isGender());
        guideService.setNickname(user.getNickname());
        guideService.setCreateTime(formattedDateTime);
        guideService.setDepartureDate(departureTime);
        guideService.setEndDate(endTime);
        guideService.setSumDay(String.valueOf(daysBetween));
        guideService.setMessage(message);
        guideService.setStatus(0);
        guideServiceService.add(guideService);
        return 1006;//添加导游服务成功
    }
}
