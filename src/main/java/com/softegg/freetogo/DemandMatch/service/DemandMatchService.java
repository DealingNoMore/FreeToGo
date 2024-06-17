package com.softegg.freetogo.DemandMatch.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.bean.GuideService;

import java.util.List;

public interface DemandMatchService {
    List<GuideService> guideMatchAccount(int did);

    int match(int did, int gid);

    int confirmed(int did, int gid);

    List<GuideService> confirmedPage(int did);

    int refuse(int did);

    int finish(int did);

    int delete(int did);

    double timeScore(int gsum, String gddate, String gedate, int dsum, String dddate, String dedate);

    double messageScore(String gmessage, String dmessage);
}
