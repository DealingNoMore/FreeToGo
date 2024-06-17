package com.softegg.freetogo.GuideService.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.Dao.GuideServiceRepository;
import com.softegg.freetogo.GuideService.bean.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @description:数据库操作接口实现类
 * @author:wuyifan
 * @date:2024/5/13 9:19
 */
@Component
public class GuideServiceServiceImpl implements GuideServiceService {
    @Autowired
    private GuideServiceRepository GuideServiceRepository;
    @Autowired
    private GuideServiceRepository guideServiceRepository;

    /**
     * @description: 查找所有游客需求
     * @param: null
     * @return: java.util.List<com.softegg.freetogo.GuideService.Bean.GuideService>
     * @author: wuyifan
     * @date: 2024/5/13 9:21
     */
    public List<GuideService> findAll() {
        System.out.println("查询成功");
        return GuideServiceRepository.findAll();
    }


    /**
     * @description: 以guideService为导游服务入库
     * @param: guideService
     * @return: void
     * @author: wuyifan
     * @date: 2024/5/13 9：22
     */
    public void add(GuideService guideService) {
        GuideServiceRepository.save(guideService);
        System.out.println("添加成功");
    }

    /**
     * @description: 获得对应序号id的服务
     * @param: id
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/10 20:02
     */
    public GuideService getGuideServiceById(int id) {
        return GuideServiceRepository.findById(id).orElse(null);
    }

    /**
     * @description: 删除对应gid导游服务
     * @param: id
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/9 22:53
     */
    public void deleteById(int gid) {
        guideServiceRepository.deleteById(gid);
        System.out.println("删除成功:" + gid);
    }

    /**
     * @description: 更新需求信息
     * @param: guideService
     * @return: void
     * @author: wuyifan
     * @date: 2024/5/10 20:05
     */
    public void update(GuideService guideService) {
        GuideServiceRepository.save(guideService);
        System.out.println("服务更新成功");
    }

    /**
     * @description: 根据手机号获得导游用户的所有服务
     * @param: phone
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/16 15:18
     */

    public List<GuideService>  getGuideServiceByPhone(String phone) {
        List<GuideService> guideServiceList =  GuideServiceRepository.findByPhone(phone);
        Iterator<GuideService> iterator = guideServiceList.iterator();
        while (iterator.hasNext()) {
            GuideService guideService = iterator.next();
            if (guideService.getStatus() == 4) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }
        return guideServiceList;
    }

    /**
     * @description: 根据城市获得导游用户的所有服务
     * @param: city
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/16 15:30
     */

    public List<GuideService>  getGuideServiceByCity(String city) {
        return GuideServiceRepository.findByCity(city);
    }

    /**
     * @description: 根据城市获得导游用户的所有服务
     * @param: city
     * @return: com.softegg.freetogo.GuideService.Bean.GuideService
     * @author: wuyifan
     * @date: 2024/5/16 15:30
     */

    public List<GuideService>  getGuideServiceByProvince(String province) {
        return GuideServiceRepository.findByProvince(province);
    }

}
