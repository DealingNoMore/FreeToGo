package com.softegg.freetogo.Api.authencation;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: 身份验证api
 * @author: zhanglinhao
 * @date: 2024/5/23 15:12
 */
@Service
public class authenticationService {
    public Boolean authenticate(String idc, String phone, String name) throws IOException {
        String url = "https://mobile3elements.shumaidata.com/mobile/verify_real_name";
        String appCode = "15704c321ae8459bb36fcadd888d67c4";

        Map<String, String> params = new HashMap<>();
        params.put("idcard", idc);
        params.put("mobile", phone);
        params.put("name", name);
        Boolean result = postForm(appCode, url, params);
        System.out.println(result);
        return result;
    }

    /**
     * 用到的HTTP工具包：okhttp 3.13.1
     * <dependency>
     * <groupId>com.squareup.okhttp3</groupId>
     * <artifactId>okhttp</artifactId>
     * <version>3.13.1</version>
     * </dependency>
     */
    public static Boolean postForm(String appCode, String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            formbuilder.add(key, params.get(key));
        }
        FormBody body = formbuilder.build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
        Response response = client.newCall(request).execute();
        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
//        String result = response.body().string();
//        return result;
        return response.code() == 200;
    }
}