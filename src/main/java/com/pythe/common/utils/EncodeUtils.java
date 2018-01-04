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
        
    	byte[] b = {0x06,0x07,(byte) 0xF4,(byte) 0xC7,0x13,0x4C,0x01,0x01,0x04,0x06,0x75,0x64,0x6B,(byte) 0xEE,0x0E};
    	System.out.println(Base64.encodeBase64(b));
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
	public static String bluetoothEncrypt(String content, String lockKeyStr) {
		
		byte sSrc[] = Base64.decodeBase64(content.getBytes());
		String[] hexArray = lockKeyStr.split(",");
		
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(NumberUtils.parseHexArray2ByteArray(hexArray), "AES");
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
	
	
	
    
    
    
}
