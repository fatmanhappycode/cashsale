package com.cashsale.filter;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author 肥宅快乐码
 * @date 2018/10/11 - 22:32
 */
public class CommonUtils {
    /*
     * 密文，用于加密解密Signature
     */
    private static final String JWT_SECRET = "fnarip*fudsgf_pbwei_65fa9sdf_jcewdiudsad56161bfof_1564d16";
    /**
     * 创建jwt
     * @param id
     * @param ttlMillis 过期的时间长度
     * @return
     * @throws Exception
     */
    public static String createJWT(String subject, long ttlMillis) throws Exception {
        // 定义签名算法为HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 获取系统时间作为签发jwt的时间
        long nowMillis=System.currentTimeMillis();
        // 使用自定义的方法generalKey生成密钥
        SecretKey key = generalKey();
        long startTime = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date()) // 签发时间
                .setSubject(subject)  // 负载的内容
                .signWith(signatureAlgorithm, key) // 采用的加密算法
                .setExpiration(new Date(nowMillis + ttlMillis)); // 过期时间
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)+"ms");
        // 生成JWT并返回
        return builder.compact();
    }
    private static SecretKey generalKey() {
        // 秘钥
        String stringKey =CommonUtils.JWT_SECRET;
        // 对密钥采用base64进行编码加密
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 创建密钥  SecretKeySpc（编码后的密钥，从第几位开始加密，要加密多少位长度，使用的加密算法）
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    /**
     * 解析JWT
     * 验证token
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
    public static void main(String[] args) {
        try {
            String token=CommonUtils.createJWT("jnxj", 30*1000L);// 系统时间单位为毫秒，这里为30秒
            // 线程睡眠单位为毫秒，当该数据超过30秒后，令牌无效
            Thread.sleep(31000);
            System.out.println(CommonUtils.parseJWT(token).getSubject());
        }catch (ExpiredJwtException e) {
            System.out.println("token过期");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}