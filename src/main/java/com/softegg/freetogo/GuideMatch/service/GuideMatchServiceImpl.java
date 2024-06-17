package com.softegg.freetogo.GuideMatch.service;

import com.softegg.freetogo.Demand.bean.Demands;
import com.softegg.freetogo.GuideService.bean.GuideService;
import com.softegg.freetogo.Demand.service.DemandsService;
import com.softegg.freetogo.User.service.UsersService;
import com.softegg.freetogo.GuideService.service.GuideServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.abs;

/**
 * description:导游服务匹配实现类
 * @author:wuyifan
 * date:2024/5/28  19:51
 */
@Component
public class GuideMatchServiceImpl implements GuideMatchService {
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
    public List<Demands> guideMatchAccount(int gid){
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        int guideServiceSumDay = Integer.parseInt(guideService.getSumDay());
        String guideServiceDepartureDate = guideService.getDepartureDate();
        String guideServiceEndDate = guideService.getEndDate();
        String guideServiceMessage = guideService.getMessage();
        List<Demands> dlist = demandsService.getDemandsByCity(guideService.getCity());
        Iterator<Demands> iterator = dlist.iterator();
        while (iterator.hasNext()) {
            Demands demand = iterator.next();
            if (demand.getStatus() > 1) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }
        int length = dlist.size();
        double[] sumScores = new double[length];
        int i = 0;
        for (Demands demands : dlist) {
            double sumScore = 0;
            int demandsSumDay = Integer.parseInt(demands.getSumDay());
            String demandsDepartureDate = demands.getDepartureDate();
            String demandsEndDate = demands.getEndDate();
            String demandsMessage = demands.getMessage();
            double timeScore = timeScore(guideServiceSumDay,guideServiceDepartureDate,guideServiceEndDate,demandsSumDay,demandsDepartureDate,demandsEndDate);
            double messageScore = messageScore(guideServiceMessage, demandsMessage);
            sumScore += timeScore + messageScore;
            sumScores[i] = sumScore;
            i++;
        }
        // 创建一个包含索引和值的列表
        List<Map.Entry<Double, Demands>> list = new ArrayList<>();
        for (int j = 0; j < sumScores.length; j++) {
            list.add(new AbstractMap.SimpleEntry<>(sumScores[j], dlist.get(j)));
        }
        // 根据sumScores的元素大小对 list 进行排序
        list.sort(new Comparator<Map.Entry<Double, Demands>>() {
            @Override
            public int compare(Map.Entry<Double, Demands> o1, Map.Entry<Double, Demands> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        List<Demands> newdList = new ArrayList<>();
        for (Map.Entry<Double, Demands> entry : list) {
            newdList.add(entry.getValue());
        }
        return newdList;
    }

    /**
     * @description: 申请匹配游客需求实现函数，游客向导游申请
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */

    @Override
    public int match(int gid, int did){
        Demands demand = demandsService.getDemandById(did);
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        demand.setStatus(1);
        List<Integer> guideServiceIdList = demand.getGuideServiceIdList();
        if (guideServiceIdList == null) {
            guideServiceIdList = new ArrayList<>();
        }
        if(!guideServiceIdList.contains(gid)){
            guideServiceIdList.add(gid);
        }
        demand.setGuideServiceIdList(guideServiceIdList);
//        List<Integer> demandIdList = guideService.getDemandIdList();
//        if (demandIdList == null) {
//            demandIdList = new ArrayList<>();
//        }
//        demandIdList.add(did);
//        guideService.setDemandIdList(demandIdList);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        System.out.println(demand.getGuideServiceIdList());
        return 1006;
    }

    /**
     * @description: 确定导游服务申请列表实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public List<Demands> confirmedPage(int gid){
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        List<Integer> demandIdList = guideService.getDemandIdList();
        List<Demands> demandsList = new ArrayList<>();
        for(int number : demandIdList) {
            Demands demand = demandsService.getDemandById(number);
            demandsList.add(demand);
        }
        return demandsList;
    }

    /**
     * @description: 确定匹配导游服务匹配实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int confirmed(int gid, int did){
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
     * @description: 取消导游服务实现函数，根据返回码进行操作
     * @param: did,gid
     * @return: int
     * author:  wuyifan
     * @date: 2024/5/28 19:52
     */
    @Override
    public int refuse(int gid){
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        int did = guideService.getDemandId();
        Demands demand = demandsService.getDemandById(gid);
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
        guideService.setDemandId(null);
        demand.setGuideServiceId(null);
        demandsService.update(demand);
        guideServiceService.update(guideService);
        return 1;
    }

    @Override
    public int delete(int gid){
        GuideService guideService = guideServiceService.getGuideServiceById(gid);
        guideService.setStatus(4);
        guideServiceService.update(guideService);
        return 1;
    }

    public static boolean isRangeWithinRange(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2) {
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
        System.out.println(startDate1);
        System.out.println(endDate1);

        LocalDate startDate2 = LocalDate.parse(gddate, formatter);
        LocalDate endDate2 = LocalDate.parse(gedate, formatter);
        System.out.println(startDate2);
        System.out.println(endDate2);

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
