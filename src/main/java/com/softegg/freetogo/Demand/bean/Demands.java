package com.softegg.freetogo.Demand.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description:数据库中表demand的对应实体类
 * @author:wuyifan
 * @date:2024/5/10 11:36
 */
@Entity
@Table(name = "demands")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Demands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer did;
    @Column
    private String phone;//游客手机号码
    @Column(name = "tourist_gender")
    private boolean touristGender;//游客性别 ture:male, false:female
    @Column
    private String nickname;//发布需求的游客昵称信息
    @Column(name = "create_time")
    private String createTime;//需求发布时间
    @Column(name = "departure_date")
    private String departureDate;//游客需求起始日期
    @Column(name = "end_date")
    private String endDate;//游客需求结束日期
    @Column(name = "sum_day")
    private String sumDay;//游客旅游总天数
    @Column
    private String province;//发布需求的目的省份
    @Column
    private String city;//发布需求的目的城市
    @Column
    private String message;//需求备注内容
    @Column
    private Integer status;//游客需求状态 0：未匹配 1：请求匹配 2：已完成匹配 3：已完成所有服务
    @Column
    private Integer guideServiceId;//游客需求最终匹配到的导游服务id
    @ElementCollection
    @Column(name = "guide_service_id_list")
    private List<Integer> guideServiceIdList;//游客需求申请匹配到的导游服务id
}
