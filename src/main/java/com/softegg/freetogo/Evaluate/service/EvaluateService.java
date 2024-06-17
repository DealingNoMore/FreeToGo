package com.softegg.freetogo.Evaluate.service;

import com.softegg.freetogo.Evaluate.bean.Evaluations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author:zhanglinhao
 * @date:2024/5/10 8:52
 */
@Service
public interface EvaluateService {
    List<Evaluations> evaluationList();//获取所有评论

//    List<Evaluations> getEListByPhone(String phone);//根据电话筛选评价

    //    List<Evaluations> getEvaluatedByPhone(String phone);
    Evaluations getEvaluationById(int eid);//根据id获取评论

    void addEvaluation(Evaluations evaluation);//添加评论

    void editEvaluation(Evaluations evaluation);//编辑评论

    void deleteEvaluation(int eid);//删除评论
}
