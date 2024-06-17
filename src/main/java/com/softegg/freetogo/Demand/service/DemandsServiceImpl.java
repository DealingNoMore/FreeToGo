package com.softegg.freetogo.Demand.service;

import com.softegg.freetogo.Demand.Dao.DemandsRepository;
import com.softegg.freetogo.Demand.bean.Demands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @description:数据库操作接口实现类
 * @author:wuyifan
 * @date:2024/5/10 19:50
 */
@Component
public class DemandsServiceImpl implements DemandsService {
    @Autowired
    private DemandsRepository DemandsRepository;

    /**
     * @description: 查找所有游客需求
     * @param: null
     * @return: java.util.List<com.softegg.freetogo.Demand.Bean.Demands>
     * @author: wuyifan
     * @date: 2024/5/10 19:53
     */
    public List<Demands> findAll() {
        System.out.println("查询成功");
        return DemandsRepository.findAll();
    }

    /**
     * @description: 以demand为游客需求入库
     * @param: demand
     * @return: void
     * @author: wuyifan
     * @date: 2024/5/10 19:55
     */
    public void add(Demands demand) {
        DemandsRepository.save(demand);
        System.out.println("添加成功");
    }

    /**
     * @description: 删除对应序号id的需求
     * @param: id
     * @return: void
     * @author: wuyifan
     * @date: 2024/5/10 19:59
     */
    public void deleteById(int did) {
        DemandsRepository.deleteById(did);
        System.out.println("删除成功对应did:");
    }

    /**
     * @description: 获得对应手机号游客的需求
     * @param: id
     * @return: com.softegg.freetogo.Demand.Bean.Demands
     * @author: wuyifan
     * @date: 2024/5/10 20:02
     */
    public Demands getDemandById(int id) {
        return DemandsRepository.findById(id).orElse(null);
    }

    /**
     * @description: 更新需求信息
     * @param: demand
     * @return: void
     * @author: wuyifan
     * @date: 2024/5/10 20:05
     */
    public void update(Demands demand) {
        DemandsRepository.save(demand);
        System.out.println("需求更新成功");
    }

    /**
     * @description: 根据手机号获得游客用户的所有需求
     * @param: phone
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/15 20:18
     */

    public List<Demands>  getDemandsByPhone(String phone) {
        List<Demands> demandsList = DemandsRepository.findByPhone(phone);
        Iterator<Demands> iterator = demandsList.iterator();
        while (iterator.hasNext()) {
            Demands demand = iterator.next();
            if (demand.getStatus() == 4) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }
        return demandsList;
    }

    /**
     * @description: 根据城市获得游客用户的所有需求
     * @param: city
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/15 20:18
     */

    public List<Demands>  getDemandsByCity(String city) {
        return DemandsRepository.findByCity(city);
    }

    /**
     * @description: 根据城市获得游客用户的所有需求
     * @param: city
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/15 20:18
     */
    public List<Demands>  getDemandsByProvince(String province) {
        return DemandsRepository.findByProvince(province);
    }


}
