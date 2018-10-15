package basic.arch.tools.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class EncrypUtils {
	/**
	 * MD5 加密
	 * 
	 * @param input
	 * @return
	 */
	public static String md5Sign(String input) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(input.getBytes("UTF-8"));

			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else {
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3DES加密
	 * 
	 * @param input
	 * @param desKey
	 * @param desIv
	 * @return
	 */
	public static String triDesEncrypt(String input, String desKey, String desIv) {
		Cipher cipher = null;
		try {
			SecureRandom sr = new SecureRandom();
			DESedeKeySpec dks = new DESedeKeySpec(desKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			Key key = keyFactory.generateSecret(dks);
			IvParameterSpec iv = new IvParameterSpec(desIv.getBytes("UTF-8"));
			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(1, key, iv, sr);
			byte[] array = cipher.doFinal(input.getBytes("UTF-8"));
			return new BASE64Encoder().encode(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3DES 解密
	 * 
	 * @param input
	 * @param desKey
	 * @param desIv
	 * @return
	 */
	public static String triDesDecrypt(String input, String desKey, String desIv) {
		Cipher cipher = null;
		try {
			SecureRandom sr = new SecureRandom();
			DESedeKeySpec dks = new DESedeKeySpec(desKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			IvParameterSpec iv = new IvParameterSpec(desIv.getBytes("UTF-8"));
			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(2, key, iv, sr);
			byte[] array = cipher.doFinal(new BASE64Decoder().decodeBuffer(input));
			return new String(array, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String desKey = "jingmiaojingmiaojingmiao";//24位以上
		String desIv = "jingmiao";//8位以上
		String input = "iloveyou";
		System.out.println("原内容："+input);
		String jiami_str = triDesEncrypt(input, desKey, desIv);
		System.out.println("加密后："+jiami_str);
		String jiemi_str = triDesDecrypt(jiami_str, desKey, desIv);
		System.out.println("解密后："+jiemi_str);
	}
}
