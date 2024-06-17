package com.softegg.freetogo.GuideMatch.service;

import com.softegg.freetogo.Demand.bean.Demands;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:抽象需求发送服务接口类
 * @author:wuyifan
 * @date:2024/5/28 19:45
 */
@Service
public interface GuideMatchService {

    List<Demands> guideMatchAccount(int gid);

    int match(int gid, int did);

    List<Demands> confirmedPage(int gid);

    int confirmed(int did, int gid);

    int refuse(int gid);

    int delete(int gid);

    double timeScore(int gsum, String gddate, String gedate, int dsum, String dddate, String dedate);

    double messageScore(String gmessage, String dmessage);
}
