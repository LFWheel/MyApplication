package com.eeesys.frame.utils;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encrpt {

	private static final String Algorithm = "DESede";

	static String defaultKey = "iloveeeesys&&ilovehealth";

	/**
	 * 创建日期2011-4-25上午10:12:38 修改日期 作者：dh *TODO 使用Base64加密算法加密字符串 return
	 */
	public static byte[] b64Encode(byte[] b) {
		Base64 base64 = new Base64();
		b = base64.encode(b);
		return b;
	}

	/**
	 * 创建日期2011-4-25上午10:15:11 修改日期 作者：dh *TODO 使用Base64加密 return
	 */
	public static byte[] b64Decode(byte[] b) {
		Base64 base64 = new Base64();
		b = base64.decode(b);
		return b;
	}

	public static String puriKey(String key) {
		String keyAddon = defaultKey;// "1234567890abcdefghijklmn";
		key = key + "";
		if (key.length() > 24) {
			key = key.substring(0, 24);
		}
		if (key.length() < 24) {
			key = keyAddon.substring(0, (24 - key.length())) + key;
		}
		return key;
	}

	public static String encryptStr(String src) {
		return encryptStr(src, defaultKey);
	}

	public static String decryptStr(String src) {
		return decryptStr(src, defaultKey);
	}

	public static String encryptStr(String src, String key) {
		return new String(b64Encode(encryptMode(puriKey(key).getBytes(),
				src.getBytes())));
	}

	public static String decryptStr(String src, String key) {
		return new String(decryptMode(puriKey(key).getBytes(),
				b64Decode(src.getBytes())));
	}

	@SuppressLint("TrulyRandom") 
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	@SuppressLint("DefaultLocale") 
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
}
