package com.softegg.freetogo.Demand.controller;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.Demand.service.DemandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description:Demand控制类，用于前后端交互
 * @author:wuyifan
 * @date:2024/5/10 11:40
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/demands")
public class DemandsController {
    @Autowired
    private DemandsService demandsService;

    @GetMapping("findAllRandom")
    public List<Demands> findAllRandom() {
        List<Demands> dlist =  demandsService.findAll();
        Collections.shuffle(dlist);
        return dlist;
    }

    @GetMapping("add")
    public String add(String phone,
                      boolean tGender,
                      String nkn,
                      String ct,
                      String dDate,
                      String eDate,
                      String sDay,
                      String province,
                      String city,
                      String message,
                      Integer status,
                      Integer guideServiceId,
                      List<Integer> guideServiceIdList) {
        Demands demand = new Demands();
        setDemands(phone, tGender, nkn, ct, dDate, eDate, sDay, province, city, message, status, demand, guideServiceId, guideServiceIdList);
        demandsService.add(demand);
        return "添加成功";
    }

    @PostMapping("delbyid")
    public int delById(@RequestBody Map<String, Object> map) {
        int did = Integer.parseInt(map.get("did").toString());
        demandsService.deleteById(did);
        return 1;
    }

    @GetMapping("findbyid")
    public Demands getUserById(int id) {
        return demandsService.getDemandById(id);
    }

    @GetMapping("update")
    public String update(int id,
                         String phone,
                         boolean tGender,
                         String nkn,
                         String ct,
                         String dDate,
                         String eDate,
                         String sDay,
                         String province,
                         String city,
                         String message,
                         Integer status,
                         Integer guideServiceId,
                         List<Integer> guideServiceIdList) {
        Demands demand = demandsService.getDemandById(id);
        setDemands(phone, tGender, nkn, ct, dDate, eDate, sDay, province, city, message, status, demand, guideServiceId, guideServiceIdList);
        demandsService.update(demand);
        return "更新成功";
    }

    @GetMapping("getByPhone")
    public List<Demands>  getByPhone(String phone) {
        System.out.println("根据手机号获取游客需求信息:" + phone);
        return demandsService.getDemandsByPhone(phone);
    }

    private void setDemands(String phone, boolean tGender, String nkn, String ct, String dDate, String eDate, String sDay, String province, String city, String message, Integer status, Demands demand,Integer guideServiceId,List<Integer> guideServiceIdList) {
        demand.setPhone(phone);
        demand.setTouristGender(tGender);
        demand.setNickname(nkn);
        demand.setCreateTime(ct);
        demand.setDepartureDate(dDate);
        demand.setEndDate(eDate);
        demand.setSumDay(sDay);
        demand.setProvince(province);
        demand.setCity(city);
        demand.setMessage(message);
        demand.setStatus(status);
        demand.setGuideServiceId(guideServiceId);
        demand.setGuideServiceIdList(guideServiceIdList);
    }


}
