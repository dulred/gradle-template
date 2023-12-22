package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.UserTimeLeft;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

//@SpringBootTest
class DemoApplicationTests {

	private final String SECRET ="eyJ1c2VydHlwZSI6ImFkbWluIiwiZXhw";
	@Test
	void contextLoads() {

	}

	@Test
	public void getToken (){
		Calendar instance  = Calendar.getInstance();
		instance.add(Calendar.SECOND,30);
//		System.out.println(instance.getTime().getTime());
//		System.out.println(new Date().getTime());
//
//		// 创建 SimpleDateFormat 对象，指定日期格式
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		// 将毫秒数转换为日期字符串
//		String formattedDate = sdf.format(new Date(instance.getTime().getTime()));
//
//		// 打印日期字符串
//		System.out.println("日期：" + formattedDate);
//
//		System.out.println(sdf.format(new Date(new Date().getTime())));

//		增加随机数
		// 创建 Random 对象
		Random random = new Random();

		// 生成随机整数，范围是1到1000
		int randomNumber = random.nextInt(1000) + 1;

		System.out.println("随机数：" + randomNumber);
		
//		传入的名字
		String name = "nishizhu";

		HashMap<String,Object> map = new HashMap<>();
		map.put("alg","HS256");
		map.put("typ","JWT");
		String token  = JWT.create()
				.withHeader(map)
				.withClaim("username",name)
				.withClaim("usertype",randomNumber)
				.withExpiresAt(instance.getTime())
				.sign(Algorithm.HMAC256(SECRET))
				;

		System.out.println(token);

	}

	@Test
	public void verifier(){
		//解密token成一个DecodedJWT对象  ，具体的信息可从该对象中继续获取
		String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VydHlwZSI6NTYyLCJleHAiOjE3MDIzNzA1MDQsInVzZXJuYW1lIjoibmlzaGl6aHUifQ.txzGwOlHm0qGDpDYvuQAcEA4jvAQMGieHVWkZCMT784";

		/**
		 * 验证token  如果token过期或错误 都会抛出相应的异常，可通过捕获异常来设置返回信息
		 */
//		DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);

		try {
			DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
			//继续获取信息 还有很多api
			System.out.println(verify.getClaim("username").asString());
			System.out.println(verify.getClaim("usertype").asInt());
		} catch (SignatureVerificationException e) {
			e.printStackTrace();
			System.out.println("签名不一致");
		} catch (TokenExpiredException e) {
			e.printStackTrace();
			System.out.println("令牌过期");
		} catch (AlgorithmMismatchException e) {
			e.printStackTrace();
			System.out.println("算法不匹配");
		} catch (InvalidClaimException e) {
			e.printStackTrace();
			System.out.println("失效的payload");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("token无效");
		}

	}


	@Test
	public void DynamicPasswordGenerator (){
		try {
			// 获取当前时间戳
			long timestamp = System.currentTimeMillis();

			// 计算当前时间所在的三分钟时间段
			long threeMinutesInterval = 3 * 60 * 1000; // 三分钟的毫秒数
			long intervalCount = (long) Math.ceil((double) timestamp / threeMinutesInterval);

			// 将传入的字符串与时间段整数值组合
			String input = "YourInputString" + intervalCount;

			// 生成哈希值（SHA-256示例）
			String dynamicPassword = generateDynamicPassword(input);

//			截取前21个字符，增加随机性
			String dp;
			if (dynamicPassword.length()>=21){
				 dp = dynamicPassword.substring(0,21);
			}else {
				 dp  = dynamicPassword;
			}

			System.out.println("动态密码：" + dp);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); 
		}
	}
	private static String generateDynamicPassword(String input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes());

		// 将字节数组转换为十六进制字符串
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}







}
