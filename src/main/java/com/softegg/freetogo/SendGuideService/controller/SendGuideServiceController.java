package com.softegg.freetogo.SendGuideService.controller;

import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import com.softegg.freetogo.SendGuideService.service.SendGuideServiceService;
import com.softegg.freetogo.User.bean.Users;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:登录服务前后端交互
 * @author:wuyifan
 * @date:2024/5/13 10:47
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/SendGuideService")
public class SendGuideServiceController {
    @Autowired
    SendGuideServiceService sendGuideServiceService;
    @Autowired
    UsersService usersService;
    @Autowired
    GuideServiceService guideServiceService;

    /**
     * @description: 发送需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/5/13 10:48
     */
    @PostMapping("register")
    public int SendDemand(@RequestBody Map<String, Object> map) {
        String phone = map.get("phone").toString();
        Users user = usersService.getUserByPhone(phone);
        if (user.isMembertype()) {
            return switch (sendGuideServiceService.sendnewGuideServiceAccount(map.get("city").toString(), map.get("phone").toString(), map.get("time").toString(), (String) map.get("remark"))) {
                case 1005 -> 1;//服务发送成功
                default -> 0;
            };
        } else {
            return 2;//用户非导游
        }
    }

    /**
     * @description: 发送该用户所有导游服务的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/5/16 11:01
     */
    @GetMapping("sendAllGuideService")
    public List<GuideService> sendAllGuideService(String phone) {
        List<GuideService> glist = guideServiceService.getGuideServiceByPhone(phone);
        return glist;
    }

    /**
     * @description: 发送需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/4 16:10
     */
    @GetMapping("GuideService")
    public GuideService GuideService(int gid) {
        return guideServiceService.getGuideServiceById(gid);
    }

}