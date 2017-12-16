package com.pythe.common.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.pythe.common.pojo.PytheResult;

/**
 * 采用MD5加密解密
 * @author tfq
 * @datetime 2011-10-13
 */
public class EncodeUtils {

	
    public static void main(String[] args) {  
        
    	
         
    }  
    
    public static String decode(String result){
        String string  = result.substring(1);
        string = string.replaceAll("[^a-z^A-Z]", "");
        System.out.println(string);
        
        String decodeResult = "";
        for(char c : string.toCharArray()){
       	 decodeResult  =  decodeResult + byteAsciiToChar((int)c - 17);
       	 //产生一个 0~9的随机数
        }
        
        return decodeResult;
    }
  
    
    /** 
     * 方法一：将char 强制转换为byte 
     * @param ch
     */  
    public static byte charToByteAscii(char ch){  
        byte byteAscii = (byte)ch;  
          
        return byteAscii;  
    }  
    /** 
     * 同理，ascii转换为char 直接int强制转换为char 
     * @param ascii 
     * @return 
     */  
    public static char byteAsciiToChar(int ascii){  
        char ch = (char)ascii;  
        return ch;  
    }
    
    public static byte[] LOCK_KEY = {32,87,47,82,54,75,63,71,48,80,65,88,17,99,45,43};

    //加密
	public static String bluetoothEncrypt(String parameter) {
		
		byte sSrc[] = Base64.decodeBase64(parameter.getBytes());

		
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(LOCK_KEY, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc);
			String encryptedStr = new String(Base64.encodeBase64(encrypted));
			
			return encryptedStr;
		}catch(Exception ex){
			System.out.println("==================> exception: " + ex);
			return null;
		}
			

	}  
	
	//解密
	public static String bluetoothDecrypt(String parameter) {
		
		byte sSrc[] = Base64.decodeBase64(parameter.getBytes());

		
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(LOCK_KEY, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(sSrc);
			String decryptedStr = new String(Base64.encodeBase64(decrypted));
			
			return decryptedStr;
		}catch(Exception ex){
			System.out.println("==================> exception: " + ex);
			return null;
		}
			

	}  
	
	/**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex.toUpperCase());
            }
            System.out.println("!!!!!!!!!!!!!!!!!!! " + sb.toString());
            return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                    return null;
            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }

    
    
    
    
    
}
