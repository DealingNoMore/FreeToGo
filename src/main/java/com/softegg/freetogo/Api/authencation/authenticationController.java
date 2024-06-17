package com.softegg.freetogo.Api.authencation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @description: 身份验证控制类
 * @author: zhanglinhao
 * @date: 2024/5/23 15:47
 */
@RestController
@RequestMapping("/api")
public class authenticationController {
    @Autowired
    com.softegg.freetogo.Api.authencation.authenticationService authenticationService;

    /**
     * @description: 身份验证
     * @author: zhanglinhao
     * @date: 2024/5/29 14:54
     */
    @GetMapping("authenticate")
    public boolean authenticate(String idc, String phone, String name) throws IOException {
        return authenticationService.authenticate(idc, phone, name);
    }
}
