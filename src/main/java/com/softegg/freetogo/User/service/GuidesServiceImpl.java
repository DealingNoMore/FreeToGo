package com.softegg.freetogo.User.service;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.User.Dao.GuidesRepository;
import com.softegg.freetogo.User.bean.Guides;
import com.softegg.freetogo.User.bean.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 数据库操作接口实现类
 * @author: zhanglinhao
 * @date: 2024/5/16 10:46
 */
@Component
public class GuidesServiceImpl implements GuidesService {
    private final static utils util = new utils();
    @Autowired
    GuidesRepository guidesRepository;
    @Autowired
    UsersService usersService;
//
//    @Override
//    public void registerToGuide(Guides guide) {
//        guidesRepository.save(guide);
//        util.printInfo("成功保存注册导游信息!");
//    }

    /**
     * @description: 注册成为导游
     * @param: guide
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/15 21:19
     */
    @Override
    public boolean registerToGuide(Guides guide) {
        util.printInfo("注册成为导游id:" + guide.getUid());
        Users user = usersService.getUserById(guide.getUid());
        user.setMembertype(true);
        guidesRepository.save(guide);
        if (user.isMembertype()) {
            util.printInfo("注册成功！");
            return true;
        }else{
            util.printInfo("注册失败！");
            return false;
        }

    }
}
