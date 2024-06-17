package com.softegg.freetogo.Login.controller;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.Login.service.LoginService;
import com.softegg.freetogo.User.Dao.GuidesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @description:登录服务前后端交互
 * @author:zhanglinhao
 * @date:2024/5/9 9:35
 */
@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/Login")
public class LoginController {

    private final static utils util = new utils();

    @Autowired
    LoginService loginService;

    @Autowired
    GuidesRepository guidesRepository;

    /**
     * @description: 登录的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: zhanglinhao
     * @date: 2024/5/9 22:44
     */
    @PostMapping("login")
    public int Login(@RequestBody Map<String, Object> map) {
        util.printInfo(map);
        util.printInfo("phone:" + map.get("phone").toString());
        util.printInfo("password" + map.get("password").toString());
        int tag = loginService.loginAccount(map.get("phone").toString(), map.get("password").toString());
        util.printInfo("LoginTag:" + tag);
        return switch (tag) {
            case 1000 -> 1;//登陆成功
            case 1001 -> 2;//密码或账号错误
            case 1002 -> 3;//该账户未注册
            case 1005 -> 6;//未输入账号密码
            case 1006 -> 7;//未输入账号
            case 1007 -> 8;//未输入密码
            default -> 0;
        };
    }

    /**
     * @description: 注册的交互逻辑
     * @param: map
     * @return: java.lang.String
     * @author: zhanglinhao
     * @date: 2024/5/9 22:45
     */
    @PostMapping("register")
    public int Register(@RequestBody Map<String, Object> map) {

        try {
            // 创建URL对象
//            URL url = new URL("http://localhost:9000/api/authenticate?idc="+ map.get("IDCard") +"&name="+map.get("name")+"&phone="+map.get("phone"));
            String baseURL = "http://localhost:9000/api/authenticate";
            String name=URLEncoder.encode(map.get("name").toString(), StandardCharsets.UTF_8);;
            String phone=URLEncoder.encode(map.get("phone").toString(), StandardCharsets.UTF_8);
            String idc = URLEncoder.encode(map.get("IDCard").toString(), StandardCharsets.UTF_8);
            URL url = new URL(baseURL+ "?idc="+idc +"&name="+name+"&phone="+phone);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法
            connection.setRequestMethod("GET");
            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("响应码： " + responseCode);
            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            // 关闭连接
            in.close();
            connection.disconnect();
            // 输出响应内容
            System.out.println("响应内容： " + content);
            if(content.toString().equals("false"))
                return 14;
        } catch (Exception e) {
            e.printStackTrace();
        }
        util.printInfo(map);
        int tag = loginService.registerAccount((String) map.get("name"), (String) map.get("password"), (String) map.get("phone"), (String) map.get("nickname"), (String) map.get("IDCard"));
        util.printInfo("RegisterTag:" + tag);
        return switch (tag) {
            case 1003 -> 4;//该账户已经注册
            case 1004 -> 5;//注册成功
            case 1008 -> 9;//身份证输入错误18位
            case 1010 -> 11;//手机号输入错误11位
            case 1011 -> 12;//密码过短最少8位
            case 1012 -> 13;//姓名未输入
            default -> 0;
        };
    }

}
