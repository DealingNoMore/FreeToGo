package com.softegg.freetogo.HeatMap.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.Demand.service.DemandsService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;

import com.softegg.freetogo.GuideService.bean.GuideService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * description:热力图实现类
 * @author:wuyifan
 * date:2024/6/6  10:51
 */
@Component
public class HeatMapServiceImpl implements HeatMapService {
    @Autowired
    DemandsService demandsService;
    @Autowired
    GuideServiceService guideServiceService;

    public static void main(String[] args) {
        List<String> provinceList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            // 读取 JSON 文件内容并解析为 JSONObject
            Object obj = parser.parse(new FileReader("C:/Users/WYF/Desktop/软件体系结构/FreeToGo/src/main/java/com/softegg/freetogo/HeatMap/service/area.json")); // 直接使用文件名表示相对路径
            JSONObject jsonObject = (JSONObject) obj;

            for (Object province : jsonObject.keySet()) {
                provinceList.add(province.toString());
            }
            System.out.println("所有省份列表：");
            for (String province : provinceList) {
                System.out.println(province);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 获得所有省份的List
     * @param: null
     * @return: List<String>
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    public List<String> getAllProvinces() {
        List<String> provinceList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            // 读取 JSON 文件内容并解析为 JSONObject
            Object obj = parser.parse(new FileReader("C:/Users/WYF/Desktop/软件体系结构/FreeToGo/src/main/java/com/softegg/freetogo/HeatMap/service/area.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // 遍历每个省份
            for (Object province : jsonObject.keySet()) {
                // 将省份添加到列表中
                provinceList.add(province.toString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return provinceList;
    }

    /**
     * @description: 获得目标省份的所有城市的List
     * @param: String
     * @return: List<String>
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    public List<String> getCitiesInProvince(String province) {
        List<String> cityList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            // 读取 JSON 文件内容并解析为 JSONObject
            Object obj = parser.parse(new FileReader("C:/Users/WYF/Desktop/软件体系结构/FreeToGo/src/main/java/com/softegg/freetogo/HeatMap/service/area.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // 获取湖南省的城市信息
            JSONObject hunanCities = (JSONObject) jsonObject.get(province);

            // 遍历每个城市并将城市名称添加到列表中
            for (Object city : hunanCities.keySet()) {
                cityList.add(city.toString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return cityList;
    }

    /**
     * @description: 获得所有省份的游客需求数目
     * @param: null
     * @return: int[]
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    @Override
    public int[] provinceDemandsHeatMap(){
        List<String> provinceList = getAllProvinces();
        int[] sum = new int[provinceList.size()];
        int i = 0;
        for (String province : provinceList) {
            List<Demands> demandsList = demandsService.getDemandsByProvince(province);
            sum[i] = demandsList.size();
            i++;
        }
        return sum;
    }

    /**
     * @description: 获得目标省份的游客需求数目
     * @param: String
     * @return: int[]
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    @Override
    public int[] cityDemandsHeatMap(String province){
        List<String> cityList = getCitiesInProvince(province);
        int[] sum = new int[cityList.size()];
        int i = 0;
        for (String city : cityList) {
            List<Demands> demandsList = demandsService.getDemandsByCity(city);
            sum[i] = demandsList.size();
            i++;
        }
        return sum;
    }

    /**
     * @description: 获得所有省份的游客需求数目
     * @param: null
     * @return: int[]
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    @Override
    public int[] provinceGuideServiceHeatMap(){
        List<String> provinceList = getAllProvinces();
        int[] sum = new int[provinceList.size()];
        int i = 0;
        for (String province : provinceList) {
            List<GuideService> guideServiceList = guideServiceService.getGuideServiceByProvince(province);
            sum[i] = guideServiceList.size();
            i++;
        }
        return sum;
    }

    /**
     * @description: 获得目标省份的游客需求数目
     * @param: String
     * @return: int[]
     * @author: wuyifan
     * @date: 2024/6/6 15:48
     */
    @Override
    public int[] cityGuideServiceHeatMap(String province){
        List<String> cityList = getCitiesInProvince(province);
        int[] sum = new int[cityList.size()];
        int i = 0;
        for (String city : cityList) {
            List<GuideService> guideServiceList = guideServiceService.getGuideServiceByCity(city);
            sum[i] = guideServiceList.size();
            i++;
        }
        return sum;
    }
}
