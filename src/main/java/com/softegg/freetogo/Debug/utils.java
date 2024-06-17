package com.softegg.freetogo.Debug;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:调试函数
 * @author: zhanglinhao
 * @date: 2024/5/29 8:41
 */
public class utils {
    /**
     * @description: 输出调试信息
     * @author: zhanglinhao
     * @date: 2024/5/29 14:52
     */
    public <T> void printInfo(T text){
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2]; // 获取调用printFileAndLine方法的方法的堆栈信息
        String fileName = element.getFileName();
        int lineNumber = element.getLineNumber();
        System.out.println("["+sdf.format(currentDate)+"]"+"File: " + fileName + ", Line: " + lineNumber + ", Info: "+ text);
    }

    /** 获取今日日期
     * @description:
     * @author: zhanglinhao
     * @date: 2024/5/29 14:52
     */
    public String getToday(){
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentDate);
    }
}
