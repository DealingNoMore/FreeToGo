package com.softegg.freetogo.Login.service;

import com.softegg.freetogo.User.Dao.GuidesRepository;
import com.softegg.freetogo.User.bean.Users;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description:登录服务实现类
 * @author:zhanglinhao
 * @date:2024/5/9 9:33
 */
@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    UsersService usersService;
    @Autowired
    GuidesRepository guidesRepository;

    /**
     * @description: 登录接口实现函数，根据返回码进行操作
     * @param: phone
     * @param: password
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/9 22:46
     */
    @Override
    public int loginAccount(String phone, String password) {
        if (phone.isEmpty() && password.isEmpty())
            return 1005;//未输入账号密码
        else if (phone.isEmpty())
            return 1006;//未输入账号
        else if (password.isEmpty())
            return 1007;//未输入密码
        if (usersService.isRegister(phone)) {
            if (usersService.getUserByPhone(phone).getPassword().equals(password))
                return 1000;//登录成功
            else
                return 1001;//密码或账号错误
        } else
            return 1002;//该账户未注册
    }

    /**
     * @description: 注册接口实现函数，根据返回码进行操作
     * @param: name
     * @param: password
     * @param: phone
     * @param: nickname
     * @param: IDCard
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/11 15:57
     */
    @Override
    public int registerAccount(String name,
                               String password,
                               String phone,
                               String nickname,
                               String IDCard) {
        if (usersService.isRegister(phone))
            return 1003;//该账户已经注册
        else if(name.isEmpty())
            return 1012;//姓名未输入
        else if (IDCard.length() != 18)
            return 1008;//身份证输入错误
        else if (phone.length() != 11)
            return 1010;//手机号输入错误
        else if (password.length() < 8)
            return 1011;//密码过于简单
        else {
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println("注册信息:姓名:" + name + "密码:" + password + "电话:" + phone + "昵称:" + nickname + "身份证:" + IDCard);
            Users user = new Users();
            user.setPhone(phone);
            user.setPassword(password);
            user.setNickname(nickname);
            user.setIdcard(IDCard);
            user.setName(name);
            user.setCreatetime((currentTime.getYear() + "-" + currentTime.getMonthValue() + "-" + currentTime.getDayOfMonth()));
            user.setGender(isMale(IDCard));
            user.setStatus(0);
            user.setReputation(60);
            user.setMembertype(false);
            usersService.add(user);
            return 1004;//注册成功
        }
    }

    boolean isMale(String IDCard) {
        System.out.println("根据身份证判断性别:" + IDCard + " 第17位:" + IDCard.charAt(16));
        return (int) IDCard.charAt(16) % 2 != 0;
    }
}
