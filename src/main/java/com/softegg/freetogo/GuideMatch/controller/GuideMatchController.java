package com.softegg.freetogo.GuideMatch.controller;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import com.softegg.freetogo.GuideMatch.service.GuideMatchService;
import com.softegg.freetogo.User.bean.Users;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:导游服务匹配前后端交互
 * @author:wuyifan
 * @date:2024/5/30 10:47
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/GuideMatch")
public class GuideMatchController {
    @Autowired
    GuideMatchService guideMatchService;
    @Autowired
    UsersService usersService;
    @Autowired
    GuideServiceService guideServiceService;

    /**
     * @description: 导游服务匹配的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/5/30 20:48
     */
    @GetMapping("register")
    public List<Demands> GuideMatch(int gid) {
        System.out.println("需匹配的gid:" + gid);
        return guideMatchService.guideMatchAccount(gid);

    }

    /**
     * @description: 申请匹配游客需求的交互逻辑
     * @param: map
     * @return: java.lang.int
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("match")
    public int Match(@RequestBody Map<String, Object> map) {
        int gid = Integer.parseInt(map.get("gid").toString());
        System.out.println("接收的gid:"+gid);
        int did = Integer.parseInt(map.get("did").toString());
        System.out.println("接收的did:"+did);
        return guideMatchService.match(gid, did);
    }


    /**
     * @description: 确定那些游客需求申请的交互逻辑
     * @param: gid
     * @return: java.lang.Demands
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @GetMapping("confirmedPage")
    public List<Demands> confirmedpage(int gid) {
        System.out.println("接收的gid:"+gid);
        return guideMatchService.confirmedPage(gid);
    }

    /**
     * @description: 确定匹配导游服务的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("confirmed")
    public int confirmed(@RequestBody Map<String, Object> map) {
        int gid = Integer.parseInt(map.get("gid").toString());
        System.out.println("接收的did:"+gid);
        int did = Integer.parseInt(map.get("did").toString());
        System.out.println("接收的did:"+did);
        return guideMatchService.confirmed(gid, did);
    }

    /**
     * @description: 取消导游服务匹配结果的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("refuse")
    public int refuse(@RequestBody Map<String, Object> map) {
        int gid = (int) map.get("gid");
        System.out.println("接收的gid:"+gid);
        return guideMatchService.refuse(gid);
    }

    /**
     * @description: 表面删除导游服务的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("delete")
    public int delete(@RequestBody Map<String, Object> map) {
        int gid = (int) map.get("gid");
        System.out.println("接收的gid:"+gid);
        return guideMatchService.delete(gid);
    }

}
