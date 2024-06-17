package com.softegg.freetogo.SendDemand.controller;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.Demand.service.DemandsService;
import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.SendDemand.service.SendDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:登录服务前后端交互
 * @author:wuyifan
 * @date:2024/5/10 23:09
 */

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/SendDemand")
public class SendDemandController {

    @Autowired
    SendDemandService sendDemandService;
    @Autowired
    DemandsService demandsService;

    utils util = new utils();

    /**
     * @description: 发送需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/5/10 22:45
     */
    @PostMapping("register")
    public String SendDemand(@RequestBody Map<String, Object> map) {
        return switch (sendDemandService.sendnewDemandAccount(map.get("city").toString(), map.get("phone").toString(), map.get("time").toString(), (String) map.get("remark"))) {
            case 1005 -> "1";//需求发送成功
            default -> "0";
        };
    }

    /**
     * @description: 发送该用户所有游客需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/5/16 11:01
     */
    @GetMapping("sendAllDemands")
    public List<Demands> sendAllDemand(String phone) {
        System.out.println("收到目标用户手机号:" + phone);
        List<Demands> dlist = demandsService.getDemandsByPhone(phone);
        System.out.println(dlist);
        return dlist;
    }

    /**
     * @description: 发送需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/4 16:12
     */
    @GetMapping("demand")
    public Demands demand(int did) {
        System.out.println("需求did:"+did);
        util.printInfo(demandsService.getDemandById(did).getStatus());
        return demandsService.getDemandById(did);
    }

}
