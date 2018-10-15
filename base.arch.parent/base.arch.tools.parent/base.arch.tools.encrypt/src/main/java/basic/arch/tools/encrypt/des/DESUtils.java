package basic.arch.tools.encrypt.des;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import basic.arch.tools.encrypt.BaseCrypto;
public class DESUtils extends BaseCrypto {

	public static final String KEY_ALGORITHM="DES";
	
	public static final String CIPHER_ALGORITHM="DES/ECB/PKCS5Padding";
	
	private  static DESUtils uniqueInstance = new DESUtils();
	/**
	 * keyString  :  D60469EF6E8F4FD18DF7A3C5754F76B8FBB8E076CB174D7997381D36DA391DD3ABD10C21831244B892F5122445B75001A97A186E9E154FAE9DF84EB5C53433C8
	 */
	private static final byte[] setKey = {68, 54, 48, 52, 54, 57, 69, 70, 54, 69, 56, 70, 52, 70, 68, 49, 56, 68, 70, 55, 65, 51, 67, 53, 55, 53, 52, 70, 55, 54, 66, 56, 70, 66, 66, 56, 69, 48, 55, 54, 67, 66, 49, 55, 52, 68, 55, 57, 57, 55, 51, 56, 49, 68, 51, 54, 68, 65, 51, 57, 49, 68, 68, 51, 65, 66, 68, 49, 48, 67, 50, 49, 56, 51, 49, 50, 52, 52, 66, 56, 57, 50, 70, 53, 49, 50, 50, 52, 52, 53, 66, 55, 53, 48, 48, 49, 65, 57, 55, 65, 49, 56, 54, 69, 57, 69, 49, 53, 52, 70, 65, 69, 57, 68, 70, 56, 52, 69, 66, 53, 67, 53, 51, 52, 51, 51, 67, 56};
	
	public static DESUtils getInstance() {
		return uniqueInstance;
	}
	
	/**
	 * 转换密钥
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private  Key toKey(byte[] key) throws DESException{
		try {
			DESKeySpec dks=new DESKeySpec(key);
			SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(KEY_ALGORITHM);
			SecretKey secretKey=keyFactory.generateSecret(dks);
			return secretKey;
		} catch (Exception e) {
			throw new DESException(e);
		}
		
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public  byte[] decrypt(byte[] data) throws DESException{
		try {
			Key k=toKey(setKey);
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE,k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new DESException(e);
		}
		
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  byte[] decrypt(byte[] data,byte[] key) throws DESException{
		try {
			Key k=toKey(key);
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE,k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new DESException(e);
		}
	
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  byte[] encrypt(byte[] data,byte[] key) throws DESException{
		try {
			Key k=toKey(key);
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE,k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new DESException(e);
		}
		
	}
	/**
	 * 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public  byte[] encrypt(byte[] data) throws DESException{
		try {
			Key k=toKey(setKey);
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE,k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new DESException(e);
		}
	
	}
	
	
	public  String encryptToBase64(byte[] data,byte[] key) throws DESException{
		try {
			byte[] ba=encrypt(data,key);
			return Base64.encodeBase64String(ba);
		} catch (Exception e) {
			throw new DESException(e);
		}
	
	}
	
	/**
	 * 生成密钥（56位）
	 * @return
	 * @throws Exception
	 */
	public  byte[] initKey() throws DESException{
		try {
			KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);
			kg.init(56);
			SecretKey secretKey=kg.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			throw new DESException(e);
		}
		
	}
	
	/**
	 * 生成密钥（64位）
	 * @return
	 * @throws Exception
	 */
	public  byte[] init64Key() throws DESException{
		try {
			KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
			kg.init(64);
			SecretKey secretKey=kg.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			throw new DESException(e);
		}
	
	}

}
