/*
@@程式代號 = EncryptUtils.java
@@程式名稱 = EncryptUtils
@@程式版本 = V1.000
@@更新日期 = 2021/06/29
@@檢查碼  = 內容由VPM自動產生
 */
package tw.com.tibame.event.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

	private static final String algorithm = "AES/ECB/PKCS5Padding";
	
	public static SecretKeySpec generateKey(String keyStr) throws NoSuchAlgorithmException {
		byte[] key = null;
		SecretKeySpec secretKey = null;
		MessageDigest sha = null;
	    try {
		    key = keyStr.getBytes("UTF-8");
		    sha = MessageDigest.getInstance("SHA-1");
		    key = sha.digest(key);
		    key = Arrays.copyOf(key, 16);
		    secretKey = new SecretKeySpec(key, "AES");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return secretKey;
	}
	
	public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
	    byte[] rawData = secretKey.getEncoded();
	    String encodedKey = Base64.getEncoder().encodeToString(rawData);
	    return encodedKey;
	}
	
	public static SecretKey convertStringToSecretKeyto(String encodedKey) {
	    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
	    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	    return originalKey;
	}
	
	public static String encrypt(String input, SecretKey key) throws Exception {
	    
	    Cipher cipher = Cipher.getInstance(algorithm);
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte[] cipherText = cipher.doFinal(input.getBytes());
	    return Base64.getEncoder()
	        .encodeToString(cipherText);
	}
	
	public static String decrypt(String cipherText, SecretKey key) throws Exception {
	    
	    Cipher cipher = Cipher.getInstance(algorithm);
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] plainText = cipher.doFinal(Base64.getDecoder()
	        .decode(cipherText));
	    return new String(plainText);
	}
}