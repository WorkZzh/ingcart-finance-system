package com.pythe.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtils {

	private static final String SECRET = "ingcart_*finance!";

	private static final int minute = 120;

	public static String createToken(String phoneNum, String password) throws Exception {
		Date date = new Date();
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, minute);
		Date expiresDate = nowTime.getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create().withHeader(map).withClaim("phoneNum", phoneNum).withClaim("password", "password")
				.withClaim("org", "ingcart").withExpiresAt(expiresDate).withIssuedAt(date)
				.sign(Algorithm.HMAC256(SECRET));
		return token;
	}

	public static Map<String, Claim> verifyToken(String token) throws Exception {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT decodedJWT = null;
		try {
			decodedJWT = verifier.verify(token);
		} catch (Exception e) {
			throw new RuntimeException("登陆凭证过期，请您重新登陆");
		}
		return decodedJWT.getClaims();
	}

	public static String IngcartTokenOperation(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String cookieToken = CookieUtils.getCookieValue(request, "ingcartToken");
		String sessionToken = (String) session.getAttribute("ingcartToken");
		try {
			if (!cookieToken.equals(sessionToken)) {
				return "登录凭证过期";
			} else {
				// 更新token
				Map<String, Claim> claims = JWTUtils.verifyToken(sessionToken);
				String name = claims.get("phoneNum").asString();
				String password = claims.get("phoneNum").asString();
				String ingcartToken = JWTUtils.createToken(name, password);
				CookieUtils.setCookie(request, response, "ingcartToken", ingcartToken);
				session.setAttribute("ingcartToken", ingcartToken);
				return "登录凭证有效";
			}
		} catch (Exception e) {
			return "登录凭证过期";
		}
	}

/*	public static void main(String[] args) throws Exception {
		String token = JWTUtils.createToken("136962258219", "8219");
		System.out.println(token);
		Map<String, Claim> claims = JWTUtils.verifyToken(token);
		System.out.println(claims.get("phoneNum").asString());
		System.out.println(claims.get("password").asString());
		Thread.sleep(3000);
		try {
			Map<String, Claim> claims_failure = JWTUtils.verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6InBhc3N3b3JkIiwib3JnIjoiaW5nY2FydCIsInBob25lTnVtIjoiMTM2OTYyMjU4MjE5IiwiZXhwIjoxNTI0NzQ0NTYxLCJpYXQiOjE1MjQ3MzczNjB9.ZOrrD4q7bwq3ynF-60mEHAguJvQtj6AYCHjiDkXWmXc");
		} catch (Exception e) {
			System.out.println("你好");
		}

	}*/

}
