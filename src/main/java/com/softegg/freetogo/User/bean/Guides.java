package com.softegg.freetogo.User.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: 导游实体类
 * @author: zhanglinhao
 * @date: 2024/5/16 10:33
 */
@Entity
@Table(name = "guides")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Guides {
    @Id
    private Integer uid;
    @Column(name = "residence")
    private String rsd;
    @Column(name = "familiarareas")
    private String fa;
    @Column(name = "synopsis")
    private String syns;
    @Column(name = "experance")
    private boolean re;
}
