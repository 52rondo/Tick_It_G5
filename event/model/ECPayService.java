package tw.com.tibame.event.model;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ECPayService {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	/**
	 * 產生檢查碼
	 * @param key
	 * @param iv
	 * @param obj
	 * @return
	 */
	public String genCheckMacValue(String urlEncode) {
		
		String urlEncode_lc = urlEncode(urlEncode).toLowerCase();
		urlEncode_lc = netUrlEncode(urlEncode_lc);
			return hash(urlEncode_lc.getBytes(), "SHA-256");
		
	}
	
	/**
	 * 將 byte array 資料做 hash md5或 sha256 運算，並回傳 hex值的字串資料
	 * @param data
	 * @param isMD5
	 * @return string
	 */
	private final static String hash(byte data[], String mode){
		MessageDigest md = null;
		try{
			if(mode == "MD5"){
				md = MessageDigest.getInstance("MD5");
			}
			else if(mode == "SHA-256"){
				md = MessageDigest.getInstance("SHA-256");
			}
		} catch(NoSuchAlgorithmException e){
		}
		return bytesToHex(md.digest(data));
	}
	
	/**
	 * 將資料做 urlEncode編碼
	 * @param data
	 * @return url encoded string
	 */
	public static String urlEncode(String data){
		String result = "";
		try{
			result = URLEncoder.encode(data, "UTF-8");
		} catch(UnsupportedEncodingException e){
			
		}
		return result; 
	}
	
	/**
	 * 將做完的urlEncode字串做轉換符合 .NET語言的轉換規則
	 * @param url
	 * @return .Net url encoded string
	 */
	private static String netUrlEncode(String url){
		String netUrlEncode = url.replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");
		return netUrlEncode;
	}
	
	/**
	 * 將 byte array 資料轉換成 hex字串值
	 * @param bytes
	 * @return string
	 */
	private final static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
