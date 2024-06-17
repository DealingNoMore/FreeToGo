package com.softegg.freetogo.Evaluate.service;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.Evaluate.Dao.EvaluateRepository;
import com.softegg.freetogo.Evaluate.bean.Evaluations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author:zhanglinhao
 * @date:2024/5/10 9:25
 */
@Component
public class EvaluateServiceImpl implements EvaluateService {
    private final static utils util = new utils();

    @Autowired
    EvaluateRepository evaluateRepository;

    /**
     * @description: 获取所有评论
     * @param: null
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/10 9:25
     */
    @Override
    public List<Evaluations> evaluationList() {
        util.printInfo("查询评论");
        return evaluateRepository.findAll();
    }

    /**
     * @description: 根据id获取评论
     * @param: eid
     * @return: com.softegg.freetogo.Evaluate.bean.Evaluations
     * @author: zhanglinhao
     * @date: 2024/5/15 8:13
     */
    @Override
    public Evaluations getEvaluationById(int eid) {
        return evaluateRepository.findById(eid).get();
    }


    /**
     * @description: 根据电话筛选评价
     * @param: phone
     * @return: java.util.List<com.softegg.freetogo.Evaluate.bean.Evaluations>
     * @author: zhanglinhao
     * @date: 2024/5/11 16:28
     */
//    @Override
//    public List<Evaluations> getEListByPhone(String phone) {
//        util.printInfo("查询" + phone + "的评论");
//        return evaluateRepository.findByEditorPhone(phone);
//    }

    /**
     * @description: 获取该用户所有被他人评价的评价
     * @param: phone
     * @return: java.util.List<com.softegg.freetogo.Evaluate.bean.Evaluations>
     * @author: zhanglinhao
     * @date: 2024/5/12 21:21
     */
//    @Override
//    public List<Evaluations> getEvaluatedByPhone(String phone) {
//        return evaluateRepository.findByEditedPhone(phone);
//    }

    /**
     * @description: 添加评论
     * @param: evaluation
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/10 21:20
     */
    @Override
    public void addEvaluation(Evaluations evaluation) {
        evaluateRepository.save(evaluation);
        util.printInfo("添加评论:" + evaluation.getEbody());
    }

    /**
     * @description: 编辑评论
     * @param: eid
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/11 17:10
     */
    @Override
    public void editEvaluation(Evaluations evaluation) {
        int eid = evaluation.getEid();
        util.printInfo("编辑评论:" + eid);
        evaluateRepository.save(evaluation);
        util.printInfo("编辑成功:" + eid);
    }

    /**
     * @description: 删除评论
     * @param: eid
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/11 17:28
     */
    @Override
    public void deleteEvaluation(int eid) {
        evaluateRepository.deleteById(eid);
        util.printInfo("删除评论:" + eid);
    }
}
