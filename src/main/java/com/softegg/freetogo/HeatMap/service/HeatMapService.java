package com.softegg.freetogo.HeatMap.service;

public interface HeatMapService {
    int[] provinceDemandsHeatMap();//获得每个省份的游客需求数目

    int[] cityDemandsHeatMap(String province);//获得目标省份的游客需求数目

    int[] provinceGuideServiceHeatMap();

    int[] cityGuideServiceHeatMap(String province);
}
