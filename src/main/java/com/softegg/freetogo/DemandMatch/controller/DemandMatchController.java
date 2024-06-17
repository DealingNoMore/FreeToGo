package com.softegg.freetogo.DemandMatch.controller;

import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import com.softegg.freetogo.DemandMatch.service.DemandMatchService;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:游客需求匹配前后端交互
 * @author:wuyifan
 * @date:2024/6/4 14:47
 */
@RestController
@RequestMapping("/DemandMatch")
public class DemandMatchController {
    @Autowired
    DemandMatchService demandMatchService;
    @Autowired
    UsersService usersService;
    @Autowired
    GuideServiceService guideServiceService;
    /**
     * @description: 导游服务匹配的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/4 14:48
     */
    @GetMapping("register")
    public List<GuideService> GuideMatch(int did) {
        System.out.println("接收的did:"+did);
        return demandMatchService.guideMatchAccount(did);
    }

    /**
     * @description: 选中导游服务的详细信息交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/4 14:48
     */
    @GetMapping("specific")
    public List<GuideService> specific(int did, int gid) {
        System.out.println("接收的did:"+did);
        return demandMatchService.guideMatchAccount(did);
    }

    /**
     * @description: 申请匹配导游服务的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("match")
    public int Match(@RequestBody Map<String, Object> map) {
        int did = (int) map.get("did");
        System.out.println("接收的did:"+did);
        int gid = (int) map.get("gid");
        System.out.println("接收的did:"+did);
        return demandMatchService.match(did, gid);
    }

    /**
     * @description: 确定那些导游服务申请的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @GetMapping("confirmedPage")
    public List<GuideService> confirmedpage(int did) {
        System.out.println("接收的did:"+did);
        return demandMatchService.confirmedPage(did);
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
        int did = Integer.parseInt(map.get("did").toString());
        System.out.println("接收的did:"+did);
        int gid = Integer.parseInt(map.get("gid").toString());
        System.out.println("接收的did:"+gid);
        return demandMatchService.confirmed(did, gid);
    }

    /**
     * @description: 取消游客需求匹配结果的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("refuse")
    public int refuse(@RequestBody Map<String, Object> map) {
        int did = (int) map.get("did");
        System.out.println("接收的did:"+did);
        return demandMatchService.refuse(did);
    }

    /**
     * @description: 取消导游服务的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("finish")
    public int finish(@RequestBody Map<String, Object> map) {
        int did = (int) map.get("did");
        System.out.println("接收的did:"+did);
        return demandMatchService.finish(did);
    }

    /**
     * @description: 表面删除游客需求的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: wuyifan
     * @date: 2024/6/11 10：12
     */
    @PostMapping("delete")
    public int delete(@RequestBody Map<String, Object> map) {
        int did = (int) map.get("did");
        System.out.println("接收的did:"+did);
        return demandMatchService.delete(did);
    }

}
