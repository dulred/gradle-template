package com.example.demo;

import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HppsTests {
    @Test
    public void connectURL(){
//       读取https 的某个网页，循环输出控制台
        try {
            URL url  = new URL("https://zhuanlan.zhihu.com/p/621231261");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input;
            while ((input = br.readLine())!=null){
                System.out.println(input);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//   123456789 包含大写 小写 数字 特殊字符
    @Test
    public void ValidPassword(){
        String password = "Your1@11";

        if (isValidPassword(password)) {
            System.out.println("Password is valid");
        } else {
            System.out.println("Password is not valid");
        }
    }
    public  boolean isValidPassword(String password) {
        // 定义密码规则的正则表达式
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(passwordPattern);

        // 匹配器
        Matcher matcher = pattern.matcher(password);

        // 返回是否匹配
        return matcher.matches();
    }

}
