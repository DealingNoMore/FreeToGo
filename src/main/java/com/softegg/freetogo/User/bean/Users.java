package com.softegg.freetogo.User.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description:数据库中表users的对应实体类
 * @author:zhanglinhao
 * @date:2024/5/7 15:36
 */
@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String createtime;
    @Column(name="IDCard")
    private String idcard;
    @Column
    private int reputation;
    @Column
    private boolean gender;//ture:male, false:female
    @Column
    private boolean membertype;//true:guide, false:visitor
    @Column
    private String phone;
    @Column
    private String nickname;
    @Column
    private int status;

    public Users(String name, String email, String psw, String ct, int rpt, String phone, String nkn, String idc, boolean gender, boolean type, int status) {
        this.name = name;
        this.email = email;
        this.password = psw;
        this.createtime = ct;
        this.reputation = rpt;
        this.phone = phone;
        this.nickname = nkn;
        this.idcard = idc;
        this.gender = gender;
        this.membertype = type;
        this.status = status;
    }
}
