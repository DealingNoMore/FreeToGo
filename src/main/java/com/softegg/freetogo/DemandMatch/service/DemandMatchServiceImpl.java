package com.softegg.freetogo.DemandMatch.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.Demand.service.DemandsService;
import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.User.service.UsersService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.abs;
import java.time.LocalDate;

/**
 * description:导游服务匹配实现类
 * @author:wuyifan
 * date:2024/5/28  19:51
 */
@Component
public class DemandMatchServiceImpl implements DemandMatchService{
    @Autowired
    DemandsService demandsService;
    @Autowired
    UsersService usersService;
    @Autowired
    GuideServiceService guideServiceService;

    /**
     * @description: 导游服务匹配实现函数，根据返回码进行操作
     * @param: city
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public List<GuideService> guideMatchAccount(int did){
        Demands demand = demandsService.getDemandById(did);
        int demandSumDay = Integer.parseInt(demand.getSumDay());
        String demandDepartureDate = demand.getDepartureDate();
        String demandEndDate = demand.getEndDate();
        String demandMessage = demand.getMessage();
        List<GuideService> guideServiceList = guideServiceService.getGuideServiceByCity(demand.getCity());
        Iterator<GuideService> iterator = guideServiceList.iterator();
        while (iterator.hasNext()) {
            GuideService guideService = iterator.next();
            if (guideService.getStatus() > 1) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }
        int length = guideServiceList.size();
        double[] sumScores = new double[length];
        int i = 0;
        for (GuideService guideService : guideServiceList) {
            double sumScore = 0;
            int guideServiceSumDay = Integer.parseInt(guideService.getSumDay());
            String guideServiceDepartureDate = guideService.getDepartureDate();
            String guideServiceEndDate = guideService.getEndDate();
            String guideServiceMessage = guideService.getMessage();
            double timeScore = timeScore(guideServiceSumDay, guideServiceDepartureDate, guideServiceEndDate, demandSumDay, demandDepartureDate, demandEndDate);
            double messageScore = messageScore(guideServiceMessage, demandMessage);
            sumScore += timeScore + messageScore;
            sumScores[i] = sumScore;
            i++;
        }
        // 创建一个包含索引和值的列表
        List<Map.Entry<Double, GuideService>> list = new ArrayList<>();
        for (int j = 0; j < sumScores.length; j++) {
            list.add(new AbstractMap.SimpleEntry<>(sumScores[j], guideServiceList.get(j)));
        }
        // 根据sumScores的元素大小对 list 进行排序
        list.sort(new Comparator<Map.Entry<Double, GuideService>>() {
            @Override
            public int compare(Map.Entry<Double, GuideService> o1, Map.Entry<Double, GuideService> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        List<GuideService> newgList = new ArrayList<>();
        for (Map.Entry<Double, GuideService> entry : list) {
            newgList.add(entry.getValue());
        }
        return newgList;
    }

    /**
     * @description: 申请匹配导游服务匹配实现函数，游客向导游申请
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int match(int did, int gid){
        Demands demand = demandsService.getDemandById(did);
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
//        List<Integer> guideServiceIdList = demand.getGuideServiceIdList();
//        if (guideServiceIdList == null) {
//            guideServiceIdList = new ArrayList<>();
//        }
//        guideServiceIdList.add(gid);
//        demand.setGuideServiceIdList(guideServiceIdList);
        List<Integer> demandIdList = guideService.getDemandIdList();
        if (demandIdList == null) {
            demandIdList = new ArrayList<>();
        }
        if(!demandIdList.contains(did)) {
            demandIdList.add(did);
        }
        guideService.setStatus(1);
        guideService.setDemandIdList(demandIdList);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        System.out.println(guideService.getDemandIdList());
        return 1006;
    }

    /**
     * @description: 确定匹配导游服务匹配实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int confirmed(int did, int gid){
        Demands demand = demandsService.getDemandById(did);
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        demand.setStatus(2);
        demand.setGuideServiceId(gid);
        guideService.setStatus(2);
        guideService.setDemandId(did);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        return 1007;
    }

    /**
     * @description: 确定导游服务申请列表实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public List<GuideService> confirmedPage(int did){
        Demands demand = demandsService.getDemandById(did);
        List<Integer> guideServiceIdList = demand.getGuideServiceIdList();
        List<GuideService> guideServiceList = new ArrayList<>();
        for(int number : guideServiceIdList) {
            GuideService guideService = guideServiceService.getGuideServiceById(number);
            guideServiceList.add(guideService);
        }
        return guideServiceList;
    }

    /**
     * @description: 取消导游服务实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int refuse(int did){
        Demands demand = demandsService.getDemandById(did);
        int gid = demand.getGuideServiceId();
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        List<Integer> demandIdList = guideService.getDemandIdList();
        if (demandIdList == null) {
            guideService.setStatus(0);
        }else {
            guideService.setStatus(1);
        }
        List<Integer> guideServiceIdList = demand.getGuideServiceIdList();
        if(guideServiceIdList == null){
            demand.setStatus(0);
        }else {
            demand.setStatus(1);
        }
        demand.setGuideServiceId(null);
        guideService.setDemandId(null);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        return 1;
    }

    /**
     * @description: 完成服务实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int finish(int did){
        Demands demand = demandsService.getDemandById(did);
        int gid = demand.getGuideServiceId();
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        demand.setStatus(3);
        demand.setGuideServiceIdList(null);
        guideService.setStatus(3);
        guideService.setDemandIdList(null);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        return 1;
    }

    /**
     * @description: 完成服务后表面删除实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int delete(int did){
        Demands demand = demandsService.getDemandById(did);
        demand.setStatus(4);
        demandsService.update(demand);
        return 1;
    }

    public static boolean isRangeWithinRange(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2) {
        // 第一个范围的起始日期必须在第二个范围内或与其起始日期相同
        return !startDate1.isBefore(startDate2) && !endDate1.isAfter(endDate2);
    }

    /**
     * @description: 时间匹配分数
     * @param: gsum、dsum、ddate、edate
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/28 20:35
     */
    @Override
    public double timeScore(int gsum, String gddate, String gedate, int dsum, String dddate, String dedate){
        double score = 0;
        int difference = gsum - dsum;
        if (abs(difference) < 15 ){
            score += 10 / (float)(abs(difference) + 1);
        }

        // 使用DateTimeFormatter将字符串转换为LocalDate对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate1 = LocalDate.parse(dddate, formatter);
        LocalDate endDate1 = LocalDate.parse(dedate, formatter);

        LocalDate startDate2 = LocalDate.parse(gddate, formatter);
        LocalDate endDate2 = LocalDate.parse(gedate, formatter);

        // 判断第一个范围是否在第二个范围内
        boolean isRange1WithinRange2 = isRangeWithinRange(startDate1, endDate1, startDate2, endDate2);

        if (isRange1WithinRange2){
            score += 100;
        }
        return score;
    }

    /**
     * @description: Jaccard相似度算法
     * @param: String
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/30 20:35
     */
    public static double jaccardSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0.0; // 如果任何一个字符串为空，直接返回0
        }

        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();

        for (char c : s1.toCharArray()) {
            set1.add(c);
        }

        for (char c : s2.toCharArray()) {
            set2.add(c);
        }

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    /**
     * @description: 余弦相似度算法
     * @param: String
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/30 20:35
     */

    private static Map<Character, Integer> getCharacterFrequency(String s) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    public static double cosineSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0.0; // 如果任何一个字符串为空，直接返回0
        }
        Map<Character, Integer> freq1 = getCharacterFrequency(s1);
        Map<Character, Integer> freq2 = getCharacterFrequency(s2);

        double dotProduct = 0.0;
        double mag1 = 0.0;
        double mag2 = 0.0;

        for (char c : freq1.keySet()) {
            if (freq2.containsKey(c)) {
                dotProduct += freq1.get(c) * freq2.get(c);
            }
            mag1 += Math.pow(freq1.get(c), 2);
        }

        for (char c : freq2.keySet()) {
            mag2 += Math.pow(freq2.get(c), 2);
        }

        mag1 = Math.sqrt(mag1);
        mag2 = Math.sqrt(mag2);

        return dotProduct / (mag1 * mag2);
    }

    /**
     * @description:  Dice系数算法
     * @param: String
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/30 20:25
     */
    public static double diceCoefficient(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0.0; // 如果任何一个字符串为空，直接返回0
        }

        int intersection = 0;

        for (int i = 0; i < s1.length() - 1; i++) {
            String bigram = s1.substring(i, i + 2);
            if (s2.contains(bigram)) {
                intersection++;
            }
        }

        int union = s1.length() + s2.length() - intersection;

        return (double) (2 * intersection) / union;
    }

    /**
     * @description:  N-gram 模型算法
     * @param: String
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/30 20:22
     */
    private static Set<String> generateNGrams(String s, int n) {
        if (s == null || s.length() < n || n <= 0) {
            throw new IllegalArgumentException("Invalid input string or n-gram size");
        }

        if (s.isEmpty()) {
            return new HashSet<>(); // 返回空集合
        }

        Set<String> nGrams = new HashSet<>();
        for (int i = 0; i <= s.length() - n; i++) {
            nGrams.add(s.substring(i, i + n));
        }
        return nGrams;
    }

    public static double bigramSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0.0; // 如果任何一个字符串为空，直接返回0
        }

        Set<String> bigrams1 = generateNGrams(s1, 2);
        Set<String> bigrams2 = generateNGrams(s2, 2);

        Set<String> commonBigrams = new HashSet<>(bigrams1);
        commonBigrams.retainAll(bigrams2);

        Set<String> allBigrams = new HashSet<>(bigrams1);
        allBigrams.addAll(bigrams2);

        return (double) commonBigrams.size() / allBigrams.size();
    }

    /**
     * @description: 备注匹配分数
     * @param: gmessage、dmessage
     * @return: float
     * @author: wuyifan
     * @date: 2024/5/28 20:35
     */
    @Override
    public double messageScore(String gmessage, String dmessage){
        double score = 0;
        double similarity1 = jaccardSimilarity(gmessage, dmessage);
        double similarity2 = cosineSimilarity(gmessage, dmessage);
        double similarity3 = diceCoefficient(gmessage, dmessage);
        double similarity4 = bigramSimilarity(gmessage, dmessage);
        score = similarity1 + similarity2 + similarity3 + similarity4;
        return score;
    }
}
