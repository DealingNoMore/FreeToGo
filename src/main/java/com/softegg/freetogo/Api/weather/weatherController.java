package com.softegg.freetogo.Api.weather;

import com.google.gson.*;
import com.softegg.freetogo.Debug.utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 查询天气服务
 * @author: zhanglinhao
 * @date: 2024/5/28 21:13
 */
@RestController
@RequestMapping("/api")
public class weatherController {
    private static final String weatherApiForWeek = "https://v1.yiketianqi.com/free/week?appid=84598529&appsecret=Z1hFDcbI&unescape=1&city=";
    private static final String weatherApiForMonth = "https://v1.yiketianqi.com/free/month?appid=84598529&appsecret=Z1hFDcbI&unescape=1&city=";
    private final utils util = new utils();

    /**
     * @description: 计算今天到开始日期和结束日期的日期差
     * @author: zhanglinhao
     * @date: 2024/5/29 14:49
     */
    private static int dateGap(String now, String expect) {
        String[] nowList = now.split("-");
        String[] expectList = expect.split("-");
        LocalDate dateNow = LocalDate.of(Integer.parseInt(nowList[0]), Integer.parseInt(nowList[1]), Integer.parseInt(nowList[2]));
        LocalDate dateExpect = LocalDate.of(Integer.parseInt(expectList[0]), Integer.parseInt(expectList[1]), Integer.parseInt(expectList[2]));
        return (int) ChronoUnit.DAYS.between(dateNow, dateExpect);
    }

    @GetMapping("weather")
    public String weatherForecast(String begin, String end, String city) {
        int nowToBegin = dateGap(util.getToday(), begin);
        int nowToEnd = dateGap(util.getToday(), end);
        Pattern patternP = Pattern.compile("\\[(.*?)\\]");
        String json = getWeather(begin, end, city);
        Matcher matcher = patternP.matcher(getWeather(begin, end, city));
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(1)); // 捕获组1是我们想要的内部字符串
        }
        // 使用Gson解析JSON字符串
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse('[' + matches.get(0) + ']').getAsJsonArray();

        // 创建一个Gson实例，用于将JsonElement对象转换为Java对象
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<String> cleanedWeatherData = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            // 简单地将每个元素转换为字符串并添加到列表中
            cleanedWeatherData.add(gson.toJson(element));
        }

        // 判断天气情况
        for (String data : cleanedWeatherData) {
            if (nowToBegin <= 0 && nowToEnd >= 0) {
                if (data.contains("雨"))
                    return "旅行期间有雨";
            }
            nowToBegin--;
            nowToEnd--;
        }
        return "天气不错，适合出行";
    }

    /**
     * @description: 获取今日起7天或30天的天气情况
     * @author: zhanglinhao
     * @date: 2024/5/29 14:48
     */
    private String getWeather(String begin, String end, String city) {
        try {
            String today = util.getToday();
            StringBuilder response = new StringBuilder();
            URL url;

            //如果结束日期距离今日小于7天，使用未来7天天气预报，否则使用未来30天天气预报
            if (dateGap(today, end) <= 7) {
                url = new URL(weatherApiForWeek + city);
            } else {
                url = new URL(weatherApiForMonth + city);
            }
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            util.printInfo("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            util.printInfo(response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
