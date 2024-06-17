package com.softegg.freetogo.GuideService.controller;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:Guide控制类，用于前后端交互
 * @author:wuyifan
 * @date:2024/5/13 9:26
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/guideService")
public class GuideServiceController {
    @Autowired
    private GuideServiceService guideServiceService;

    @GetMapping("findAll")
    public List<GuideService> findAll() {
        return guideServiceService.findAll();
    }

    @GetMapping("add")
    public String add(String phone,
                      boolean gGender,
                      String nkn,
                      String ct,
                      String dDate,
                      String eDate,
                      String sDay,
                      String procvince,
                      String city,
                      String message,
                      Integer status,
                      Integer demandId,
                      List<Integer> demandIdList) {
        GuideService guideService = new GuideService();
        setDemands(phone, gGender, nkn, ct, dDate, eDate, sDay, procvince, city, message, status, guideService, demandId, demandIdList);
        guideServiceService.add(guideService);
        return "添加成功";
    }

    @PostMapping("delbyid")
    public String delById(@RequestBody Map<String, Object> map) {
        int gid = (int) map.get("gid");
        guideServiceService.deleteById(gid);
        return "1";
    }

    @GetMapping("findbyid")
    public GuideService getUserById(int gid) {
        return guideServiceService.getGuideServiceById(gid);
    }

    @GetMapping("getByPhone")
    public List<GuideService>  getByPhone(String phone) {
        System.out.println("根据手机号获取游客需求信息:" + phone);
        return guideServiceService.getGuideServiceByPhone(phone);
    }

    @GetMapping("update")
    public String update(int id,
                         String phone,
                         boolean gGender,
                         String nkn,
                         String ct,
                         String dDate,
                         String eDate,
                         String sDay,
                         String province,
                         String city,
                         String message,
                         Integer status,
                         Integer demandId,
                         List<Integer> demandIdList) {
        GuideService guideService = guideServiceService.getGuideServiceById(id);
        setDemands(phone, gGender, nkn, ct, dDate, eDate, sDay, province, city, message, status, guideService, demandId, demandIdList);
        guideServiceService.update(guideService);
        return "更新成功";
    }

    private void setDemands(String phone, boolean gGender, String nkn, String ct, String dDate, String eDate, String sDay, String province, String city, String message, Integer status, GuideService guideservice, Integer demandId, List<Integer> demandIdList) {
        guideservice.setPhone(phone);
        guideservice.setGuideGender(gGender);
        guideservice.setNickname(nkn);
        guideservice.setCreateTime(ct);
        guideservice.setDepartureDate(dDate);
        guideservice.setEndDate(eDate);
        guideservice.setSumDay(sDay);
        guideservice.setProvince(province);
        guideservice.setCity(city);
        guideservice.setMessage(message);
        guideservice.setStatus(status);
        guideservice.setDemandId(demandId);
        guideservice.setDemandIdList(demandIdList);
    }
}
