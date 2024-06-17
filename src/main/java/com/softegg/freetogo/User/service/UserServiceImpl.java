package com.softegg.freetogo.User.service;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.User.Dao.UsersRepository;
import com.softegg.freetogo.User.bean.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 数据库操作接口实现类
 * @author: zhanglinhao
 * @date: 2024/5/8 8:50
 */
@Component
public class UserServiceImpl implements UsersService {
    private final static utils util = new utils();
    @Autowired
    private UsersRepository usersRepository;

    /**
     * @description: 查找所有用户
     * @param: null
     * @return: java.util.List<com.softegg.freetogo.User.Bean.Users>
     * @author: zhanglinhao
     * @date: 2024/5/9 22:52
     */
    public List<Users> findAll() {
        util.printInfo("查询成功");
        return usersRepository.findAll();
    }

    /**
     * @description: 以user为用户入库
     * @param: user
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/9 22:53
     */
    public void add(Users user) {
        usersRepository.save(user);
        util.printInfo("添加成功:" + user);
    }

    /**
     * @description: 删除对应id用户
     * @param: id
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/9 22:53
     */
    public void deleteById(int id) {
        usersRepository.deleteById(id);
        util.printInfo("删除成功:" + id);
    }

    /**
     * @description: 获得对应id用户
     * @param: id
     * @return: com.softegg.freetogo.User.Bean.Users
     * @author: zhanglinhao
     * @date: 2024/5/9 22:54
     */
    public Users getUserById(int id) {
        return usersRepository.findById(id).orElse(null);
    }

    /**
     * @description: 更新用户信息
     * @param: user
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/9 22:54
     */
    public void update(Users user) {
        usersRepository.save(user);
        util.printInfo("更新成功:");
        showInfo(user);
    }

    /**
     * @description: 判断该手机号（账号）是否已经入库
     * @param: phone
     * @return: boolean
     * @author: zhanglinhao
     * @date: 2024/5/9 22:54
     */
    public boolean isRegister(String phone) {
        Users users = usersRepository.findByPhone(phone);
        util.printInfo("正在验证用户是否注册:" + users);
        if (users == null) {
            util.printInfo("用户未注册");
            return false;
        } else {
            util.printInfo("用户已注册");
            return true;
        }
    }

    /**
     * @description: 根据手机号获得用户
     * @param: phone
     * @return: com.softegg.freetogo.User.Bean.Users
     * @author: zhanglinhao
     * @date: 2024/5/9 22:55
     */
    public Users getUserByPhone(String phone) {
        util.printInfo("通过手机号查找用户:" + phone);
        return usersRepository.findByPhone(phone);
    }

    /**
     * @description: 判断是否是导游
     * @param: phone
     * @return: boolean
     * @author: zhanglinhao
     * @date: 2024/5/15 21:32
     */
    @Override
    public boolean isGuide(String phone) {
        util.printInfo("判断是不是导游:" + phone);
        Users user = getUserByPhone(phone);
        if (user.isMembertype()) {
            util.printInfo("是导游:" + phone);
            return true;
        } else {
            util.printInfo("不是导游:" + phone);
            return false;
        }
    }

    /**
     * @description: 查找uid
     * @param: phone
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/16 10:54
     */
    @Override
    public int getIdByPhone(String phone) {
        Users user = usersRepository.findByPhone(phone);
        if (user == null) {
            util.printInfo("该电话号码未注册");
            return -1;
        } else {
            util.printInfo("查询到的id:" + user.getUid());
            return user.getUid();
        }
    }

    /**
     * @description:打印所有信息
     * @author: zhanglinhao
     * @date: 2024/5/16 15:55
     */
    @Override
    public void showInfo(Users user) {
        util.printInfo(user.getUid());
        util.printInfo(user.getName());
        util.printInfo(user.getPhone());
        util.printInfo(user.getPassword());
        util.printInfo(user.getNickname());
        util.printInfo(user.getCreatetime());
        util.printInfo(user.getIdcard());
        util.printInfo(user.getReputation());
        util.printInfo(user.getStatus());
        util.printInfo(user.isGender());
        util.printInfo(user.isMembertype());
    }
}
