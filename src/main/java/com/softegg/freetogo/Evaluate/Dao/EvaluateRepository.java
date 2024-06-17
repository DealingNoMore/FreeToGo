package com.softegg.freetogo.Evaluate.Dao;

import com.softegg.freetogo.Evaluate.bean.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 继承Jpa数据库接口类
 * @author: zhanglinhao
 * @date: 2024/5/10 9:27
 */
public interface EvaluateRepository extends JpaRepository<Evaluations, Integer> {
//    List<Evaluations> findByEditorPhone(String phone);

//    List<Evaluations> findByEditedPhone(String phone);
}
