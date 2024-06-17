package com.softegg.freetogo.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * @description: 对验证码进行生成和存储
 * @author: zhanglinhao
 * @date: 2024/5/29 15:59
 */
public class TempTable {

    private final static String url = "jdbc:mysql://localhost:3306/freetogo";
    private final static String user = "root";
    private final static String password = "zlh1260788704";

    /**
     * @description: 创建表并且将生成的验证码存在数据库中
     * @author: zhanglinhao
     * @date: 2024/5/30 15:31
     */
    public String createTempTable(String phone) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 创建表的SQL语句
            String sql = "CREATE TABLE "+phone+" (" +
                    "code VARCHAR(4) PRIMARY KEY " +
                    ")";

            // 执行SQL语句
            stmt.execute(sql);
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                int randomNumber = random.nextInt(10); // 生成一个0-9之间的随机整数
                sb.append(randomNumber); // 将随机数添加到StringBuilder对象中
            }
            String randomString = sb.toString();
            stmt.execute("INSERT INTO smscode (code) VALUES ("+randomString+") ");
            return randomString;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * @description: 删除生成的表
     * @author: zhanglinhao
     * @date: 2024/5/30 15:32
     */
    public void deleteTempTable(String phone){
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 删除表的SQL语句
            String sql = "DROP TABLE IF EXISTS "+phone+";";

            // 执行SQL语句
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
