package com.softegg.freetogo.GuideService.Dao;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.bean.GuideService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:继承Jpa数据库接口类
 * @author:wuyifan
 * @date:2024/5/13 9:17
 */
public interface GuideServiceRepository extends JpaRepository<GuideService, Integer> {
    List<GuideService> findByPhone(String phone);

    List<GuideService> findByCity(String city);

    List<GuideService> findByProvince(String province);
}
