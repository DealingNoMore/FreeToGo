package com.softegg.freetogo.GuideService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description:数据库中表guideService的对应实体类
 * @author:wuyifan
 * @date:2024/5/13 9:14
 */
@Entity
@Table(name = "guideService")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GuideService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    @Column
    private String phone;//导游手机号码
    @Column(name = "guide_gender")
    private boolean guideGender;//导游性别 ture:male, false:female
    @Column
    private String nickname;//发布需求的导游昵称信息
    @Column(name = "create_time")
    private String createTime;//服务发布时间
    @Column(name = "departure_date")
    private String departureDate;//导游服务起始日期
    @Column(name = "end_date")
    private String endDate;//导游服务结束日期
    @Column(name = "sum_day")
    private String sumDay;//导游服务总天数
    @Column
    private String province;//导游服务的目的省份
    @Column
    private String city;//导游服务的目的城市
    @Column
    private String message;//导游服务备注内容
    @Column
    private Integer status;//导游服务状态 0：未匹配 1：请求匹配 2：已完成匹配 3：已完成所有服务
    @Column
    private Integer demandId;//导游服务最终匹配到的游客需求id
    @ElementCollection
    @Column
    private List<Integer> demandIdList;//游客需求申请匹配到的导游服务id
}