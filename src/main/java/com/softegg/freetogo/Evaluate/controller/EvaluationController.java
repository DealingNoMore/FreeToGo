package com.softegg.freetogo.Evaluate.controller;

import com.softegg.freetogo.Debug.utils;
import com.softegg.freetogo.Evaluate.bean.Evaluations;
import com.softegg.freetogo.Evaluate.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description: 对评价的操作
 * @author: zhanglinhao
 * @date: 2024/5/11 17:33
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluationController {
    private final static utils util = new utils();
    @Autowired
    EvaluateService evaluateService;

    /**
     * @description: 获取所有需求
     * @param: null
     * @return: java.util.List<com.softegg.freetogo.Evaluate.bean.Evaluations>
     * @author: zhanglinhao
     * @date: 2024/5/12 0:11
     */
    @GetMapping("findAllEvaluation")
    public List<Evaluations> findAllEvaluation() {
        return evaluateService.evaluationList();
    }

    /**
     * @description: 添加评价
     * @param: ebody
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/12 0:28
     */
    @PostMapping("addEvaluation")
    public int addEvaluation(@RequestBody Map<String, String> ebody) {
        LocalDateTime currentTime = LocalDateTime.now();
        Evaluations evaluation = new Evaluations();
        System.out.println(Integer.parseInt(ebody.get("did")));
        evaluation.setEid(Integer.parseInt(ebody.get("did")));
        evaluation.setEbody(ebody.get("ebody"));
        evaluation.setSatisfaction(Integer.parseInt(ebody.get("satisfaction")));
        evaluation.setCt(currentTime.toString());
        evaluation.setMt(currentTime.toString());
        evaluateService.addEvaluation(evaluation);
        return 1;
    }

    /**
     * @description: 获取该用户对别人的评价
     * @param: phone
     * @return: java.util.List<com.softegg.freetogo.Evaluate.bean.Evaluations>
     * @author: zhanglinhao
     * @date: 2024/5/12 0:30
     */
//    @GetMapping("evaluationByPhone")
//    public List<Evaluations> evaluationByPhone(String phone) {
//        return evaluateService.getEListByPhone(phone);
//    }
    @GetMapping("getEvaluation")
    public Evaluations getEvaluation(int eid) {
        try{
        return evaluateService.getEvaluationById(eid);}

        catch (Exception e){
            return new Evaluations();
        }
    }

    /**
     * @description: 编辑评论
     * @param: eid
     * @param: ebody
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/12 13:31
     */
    @PostMapping("editEvaluation")
    public void editEvaluation(@RequestBody Map<String, String> ebody) {
        util.printInfo("接收的json:" + ebody);
        LocalDateTime currentTime = LocalDateTime.now();
        Evaluations evaluation = new Evaluations(Integer.parseInt(ebody.get("eid")), ebody.get("ct"), currentTime.toString(), ebody.get("ebody"), Integer.parseInt(ebody.get("satisfaction")));
        evaluateService.editEvaluation(evaluation);
    }

    /**
     * @description: 删除评论
     * @param: eid
     * @return: void
     * @author: zhanglinhao
     * @date: 2024/5/12 13:32
     */
    @GetMapping("deleteEvaluation")
    public void deleteEvaluation(int eid) {
        evaluateService.deleteEvaluation(eid);
    }

    /**
     * @description: 获取该用户的受满意程度
     * @param: phone
     * @return: int
     * @author: zhanglinhao
     * @date: 2024/5/12 13:39
     */
//    @GetMapping("getSatisfaction")
//    public String getSatisfaction(String phone) {
//        List<Evaluations> elist = evaluateService.getEvaluatedByPhone(phone);
//        float sumOfSatisfaction = 0;
//        for (Evaluations evaluation : elist) {
//            sumOfSatisfaction += evaluation.getSatisfaction();
//        }
//        util.printInfo("查询满意度:"+ sumOfSatisfaction);
//        return Float.toString(sumOfSatisfaction /elist.size());
//    }
}
