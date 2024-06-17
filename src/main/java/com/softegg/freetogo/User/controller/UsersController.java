package com.softegg.freetogo.User.controller;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.User.bean.Guides;
import com.softegg.freetogo.User.bean.Users;
import com.softegg.freetogo.User.service.GuidesService;
import com.softegg.freetogo.User.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:Users控制类，用于前后端交互
 * @author:zhanglinhao
 * @date:2024/5/8 8:28
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UsersController {
    private final static utils util = new utils();
    @Autowired
    UsersService usersService;
    @Autowired
    GuidesService guidesService;

    /**
     * @description: 查找users表中所有数据
     * @param: null
     * @return: java.util.List<com.softegg.freetogo.User.bean.Users>
     * @author: zhanglinhao
     * @date: 2024/5/10 8:16
     */
    @GetMapping("findAll")
    public List<Users> findAll() {
        util.printInfo("响应查找所有数据");
        return usersService.findAll();
    }

    /**
     * @description: 新增用户
     * @author: zhanglinhao
     * @date: 2024/5/10 8:17
     */
    @GetMapping("add")
    public String add(String name,
                      String email,
                      String password,
                      String createtime,
                      int reputation,
                      String phone,
                      String nickname,
                      String IDCard,
                      boolean gender,
                      boolean type,
                      int status) {
        Users user = new Users(name, email, password, createtime, reputation, phone, nickname, IDCard, gender, type, status);
        usersService.add(user);
        util.printInfo("添加用户");
        return "添加成功";
    }

    /**
     * @description: 根据id删除用户
     * @param: id
     * @return: java.lang.String
     * @author: zhanglinhao
     * @date: 2024/5/10 8:17
     */
    @GetMapping("delbyid")
    public String delById(int id) {
        usersService.deleteById(id);
        util.printInfo("通过id删除用户");
        return "删除成功";
    }

    /**
     * @description: 根据id获取用户数据
     * @param: id
     * @return: com.softegg.freetogo.User.bean.Users
     * @author: zhanglinhao
     * @date: 2024/5/10 8:17
     */
    @GetMapping("findbyid")
    public Users getUserById(int id) {
        util.printInfo("通过id获取用户数据");
        return usersService.getUserById(id);
    }

    /**
     * @description: 使用GET接收参数更新用户信息
     * @author: zhanglinhao
     * @date: 2024/5/10 8:17
     */
    @GetMapping("update")
    public String update(String name,
                         String email,
                         String password,
                         String createtime,
                         int reputation,
                         String phone,
                         String nickname,
                         String IDCard,
                         boolean gender,
                         boolean type,
                         int status) {
        Users User = usersService.getUserByPhone(phone);
        util.printInfo(User);
        setUsers(name, email, password, createtime, reputation, phone, nickname, IDCard, gender, type, status, User);
        usersService.update(User);
        util.printInfo("更新用户信息:" + User);
        return "更新成功";
    }

    /**
     * @description: 使用POST接受参数更新用户信息
     * @param: user
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/10 19:45
     */
    @PostMapping("pupdate")
    public boolean pupdate(@RequestBody Map<String, Map<String, Object>> user) {
        Map<String, Object> ubody = user.get("user");

        util.printInfo(ubody);
//        util.printInfo(ubody.get("uid"));
        Users User = new Users((int) ubody.get("uid"), (String) ubody.get("name"), (String) ubody.get("email"), (String) ubody.get("password"), (String) ubody.get("createtime"), (String) ubody.get("idcard"), (int) ubody.get("reputation"), (boolean) ubody.get("gender"), (boolean) ubody.get("membertype"), (String) ubody.get("phone"), (String) ubody.get("nickname"), (int) ubody.get("status"));
        usersService.update(User);
        return true;
    }

    @PostMapping("Pupdate")
    public boolean Pupdate(@RequestBody Map<String, Users> user) {
        Users ubody = user.get("user");
        util.printInfo(ubody);
//        util.printInfo(ubody.get("uid"));
        Users User = new Users(ubody.getUid(), ubody.getName(), ubody.getEmail(), ubody.getPassword(), ubody.getCreatetime(), ubody.getIdcard(), ubody.getReputation(), ubody.isGender(), ubody.isMembertype(), ubody.getPhone(), ubody.getNickname(), ubody.getStatus());
        usersService.update(User);
        return true;
    }

    /**
     * @description: 通过手机号获取用户信息
     * @param: phone
     * @return: com.softegg.freetogo.User.bean.Users
     * @author: zhanglinhao
     * @date: 2024/5/10 8:17
     */
    @GetMapping("getByPhone")
    public Users getByPhone(String phone) {
        util.printInfo("根据手机号获取用户信息:" + phone);
        util.printInfo(usersService.getUserByPhone(phone));
        return usersService.getUserByPhone(phone);
    }

    /**
     * @description: 判断是否是导游
     * @param: phone
     * @return: boolean
     * @author: zhanglinhao
     * @date: 2024/5/15 20:03
     */
    @GetMapping("isGuide")
    public boolean isGuide(String phone) {
        util.printInfo("查寻是否是导游:" + phone);
        return usersService.isGuide(phone);
    }

    /**
     * @description: 获取id
     * @param: phone
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/16 11:13
     */
    @GetMapping("getUid")
    public int getUid(String phone) {
        util.printInfo("搜索uid使用的电话:" + phone);
        return usersService.getIdByPhone(phone);
    }


    /**
     * @description: 注册成为导游
     * @param: guide
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/16 10:15
     */
//    @PostMapping("registerToGuide")
//    public int registerToGuide(@RequestBody Map<String,Map<String,String>> grequesting) {//Map<String, Map<String, Object>> gbody) {
//        util.printInfo("接收的注册请求:"+grequesting);
//        Map<String,String> gbody = grequesting.get("registerToGuide");
//        util.printInfo("处理后的注册请求:"+gbody);
//        Guides guide = new Guides();
//        guide.setUid(Integer.parseInt(gbody.get("uid")));
//        guide.setRsd(gbody.get("rsd"));
//        guide.setFa(gbody.get("fa"));
//        guide.setSyns(gbody.get("syns"));
//        guide.setRe(Boolean.parseBoolean(gbody.get("re")));
//        if(guidesService.registerToGuide(guide))
//            return 1;
//        else
//            return 0;
//    }
    @PostMapping("registerToGuide")
    public int registerToGuide(@RequestBody Map<String,Guides> grequesting) {//Map<String, Map<String, Object>> gbody) {
        util.printInfo(grequesting);
        Guides guide = grequesting.get("registerToGuide");
        util.printInfo(guide);
        if(guidesService.registerToGuide(guide))
            return 1;
        else
            return 0;
    }

    /**
     * @description: 设置user属性
     * @author: zhanglinhao
     * @date: 2024/5/10 8:18
     */
    private void setUsers(String name,
                          String email,
                          String psw,
                          String ct,
                          int rpt,
                          String phone,
                          String nkn,
                          String idc,
                          boolean gender,
                          boolean type,
                          int status,
                          Users user) {
        user.setName(name);
        user.setGender(gender);
        user.setPassword(psw);
        user.setEmail(email);
        user.setReputation(rpt);
        user.setMembertype(type);
        user.setCreatetime(ct);
        user.setPhone(phone);
        user.setNickname(nkn);
        user.setIdcard(idc);
        user.setStatus(status);
    }
}
