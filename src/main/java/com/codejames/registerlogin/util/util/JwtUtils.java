package com.codejames.registerlogin.util.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token 工具类
 *
 * @author ch
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * Created at 2020/7/30 2:23 下午
 */
@Component
@Slf4j
public class JwtUtils {

  // 过期时间
  private static long expire = 604800;
  // 秘钥
  private static String secret = "HSyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";

  /**
   * 创建一个token
   *
   * @param userId
   * @return
   */
  public String generateToken(String userId) {
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + expire);
    return Jwts.builder().
            setHeaderParam("type", "JWT").
            setSubject(userId).
            setIssuedAt(now).
            setExpiration(expireDate).
            signWith(
            SignatureAlgorithm.HS512, secret).
            compact();
  }

  /**
   * 解析token
   */
  public Claims getClaimsByToken(String token) {
    try {
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      System.out.println("validate is token error");
      return null;
    }
  }

  /**
   * 判断 token 是否过期
   */
  public boolean isTokenExpired(Date expiration){
    return expiration.before(new Date());
  }

  /**
   * 验证token
   * @param token
   * @return
   */
  public boolean isVerify(String token){
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      log.info("verify succession!!!");
      return true;
    }catch (JwtException e){
      log.info("verify failure");
      return false;
    }
  }

  public static void main(String[] args) {
    JwtUtils jwtUtils = new JwtUtils();
    String token = jwtUtils.generateToken("xwj");
    System.out.println(jwtUtils.isVerify(token));
    System.out.println(jwtUtils.generateToken("xwj"));
    System.out.println(jwtUtils.getClaimsByToken(token));

    System.out.println(new Date());
  }
}
