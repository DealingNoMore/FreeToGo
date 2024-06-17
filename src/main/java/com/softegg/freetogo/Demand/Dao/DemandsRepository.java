package com.softegg.freetogo.Demand.Dao;

import com.softegg.freetogo.Demand.bean.Demands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:继承Jpa数据库接口类
 * @author:wuyifan
 * @date:2024/5/10 19:50
 */
public interface DemandsRepository extends JpaRepository<Demands, Integer> {
    List<Demands> findByPhone(String phone);

    List<Demands> findByCity(String city);

    List<Demands> findByProvince(String province);
}
