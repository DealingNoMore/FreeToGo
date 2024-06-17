package com.softegg.freetogo.User.Dao;

import com.softegg.freetogo.User.bean.Guides;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 继承Jpa数据库接口类
 * @author: zhanglinhao
 * @date: 2024/5/16 10:44
 */
public interface GuidesRepository extends JpaRepository<Guides, Integer> {
}
