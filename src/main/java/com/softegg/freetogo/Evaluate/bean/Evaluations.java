package com.softegg.freetogo.Evaluate.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description:
 * @author:zhanglinhao
 * @date:2024/5/10 9:07
 */
@Entity
@Table(name = "evaluation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Evaluations {
    @Id
    private int eid;//评价id
    @Column(name = "createtime")
    private String ct;//创建日期
    @Column(name = "modifytime")
    private String mt;//编辑日期
    @Column
    private String ebody;//评价本体
    @Column
    private int satisfaction;//满意度整数1-5
}
