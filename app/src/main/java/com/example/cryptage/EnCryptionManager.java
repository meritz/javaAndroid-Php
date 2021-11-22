package com.example.cryptage;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EnCryptionManager {
    private static  EnCryptionManager instance = null;
    private  final static  String TOKEN_KEY="mykey@91mykey@91";
    MessageDigest digest = null;
    String hash;

    private EnCryptionManager(){

    }

    public static EnCryptionManager getInstance(){
        if (instance == null){
            instance = new EnCryptionManager();
        }
        return instance;
    }

    public static String encrypt(String plain){
        try{
            System.out.println("Plain text: " +plain);
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(TOKEN_KEY.getBytes("utf-8"),"AES"), new IvParameterSpec(iv));
            byte[] cipherText = cipher.doFinal(plain.getBytes("utf-8"));
            byte[] ivAndCipherText = getCombinedArray(iv,cipherText);
            return Base64.encodeToString(ivAndCipherText,Base64.NO_WRAP );

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public  static  String decrypt(String encoded){
        try{
            byte[] ivAndCipherText = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] iv = Arrays.copyOfRange(ivAndCipherText, 0,16);
            byte[] cipherText = Arrays.copyOfRange(ivAndCipherText,16,ivAndCipherText.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(TOKEN_KEY.getBytes("utf-8"),"AES"),new IvParameterSpec(iv));
            return new String(cipher.doFinal(cipherText), "utf-8");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static  byte[] getCombinedArray(byte[] one, byte[] two){
        byte[] combined = new  byte[one.length + two.length];
        for (int i = 0; i < combined.length; i++){
            combined[i] = i < one.length? one[i]:two[i-one.length];

        }
        return combined;
    }

    private static String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i<bytes.length; i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
