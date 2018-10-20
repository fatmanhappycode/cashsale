package com.cashsale.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

/**
 * RSA加密、签名
 * @author Sylvia
 * 2018年10月20日
 */
public class RSAUtil {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * 初始化密钥
	 * </p>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		
		//设置长度
		keyPairGen.initialize(512);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//获取公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		//获取密钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		//将公钥和密钥存进keyMap中
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		
		return keyMap;
	}

	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return Base64Util.encode(signature.sign());
	}

	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 *
	 * @return
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Util.decode(sign));
	}

	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		//用密钥初始化cipher（Cipher.DECRYPT_MODE：解密模式）
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		
		byte[] keyBytes = Base64Util.decode(publicKey);
		//实例化X509EncodedKeySpec对象
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		//实例化KeyFactory对象
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//获得key对象
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) 
			{
				//加密
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} 
			else 
			{
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		
		byte[] encryptedData = out.toByteArray();
		out.close();
		
		return encryptedData;
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		
		//获得私钥密钥字节数组
		byte[] keyBytes = Base64Util.decode(privateKey);
		//实例化PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		//实例化KeyFactory，并指定KEY_ALGORITHM（=RSA）算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		//获得Key对象
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		//返回实现指定转换的 Cipher 对象（keyFactory.getAlgorithm()获取算法名称（RSA），作为转换名称）
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		//用密钥初始化cipher（Cipher.ENCRYPT_MODE：加密模式）
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) 
			{
				//加密
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			}
			else 
			{
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		
		return encryptedData;
	}

	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		//getEncoded：密钥根据标准形式进行编码，再将其编码成base64字符串
		return Base64Util.encode(key.getEncoded());
	}

	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		//getEncoded：密钥根据标准形式进行编码，再将其编码成base64字符串
		return Base64Util.encode(key.getEncoded());
	}
	
}

