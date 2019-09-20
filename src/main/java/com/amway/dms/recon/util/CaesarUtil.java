package com.amway.dms.recon.util;

import java.util.Scanner;

public class CaesarUtil {
	public static final String ALPHABET = "@$%*01234abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ56789!#^&";
	 
    public static String encrypt(String plainText, int shiftKey)
    {
    	String cipherText = "";
//    	System.out.println("plainText" + plainText);
    	if(plainText!=null) {
        //plainText = plainText.toLowerCase();
        
        for (int i = 0; i < plainText.length(); i++)
        {
            int charPosition = ALPHABET.indexOf(plainText.charAt(i));
            if(charPosition>=0) {
	            int keyVal = (shiftKey + charPosition) % 70;
	            char replaceVal = ALPHABET.charAt(keyVal);
	            cipherText += replaceVal;
            } else {
            	cipherText += plainText.charAt(i);
            }
        }
    	}
        return cipherText;
    }
 
    public static String decrypt(String cipherText, int shiftKey)
    {
    	String plainText = "";
    	if(cipherText!=null) {
        //cipherText = cipherText.toLowerCase();
        
        for (int i = 0; i < cipherText.length(); i++)
        {
            int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
            if(charPosition>=0) {
	            int keyVal = (charPosition - shiftKey) % 70;
	            if (keyVal < 0)
	            {
	                keyVal = ALPHABET.length() + keyVal;
	            }
	            char replaceVal = ALPHABET.charAt(keyVal);
	            plainText += replaceVal;
            } else {
            	plainText += cipherText.charAt(i);
            }
        }
    	}
        return plainText;
    }
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String for Encryption: ");
        String message = new String();
        message = sc.next();
        System.out.println(encrypt(message, 3));
        System.out.println(decrypt(encrypt(message, 3), 3));
        sc.close();
    }
}
