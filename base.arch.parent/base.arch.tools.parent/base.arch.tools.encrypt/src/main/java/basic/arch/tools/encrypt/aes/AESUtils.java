package basic.arch.tools.encrypt.aes;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic.arch.tools.encrypt.Base64Util;
import basic.arch.tools.encrypt.BaseCrypto;

/**
 * DES encrypt and decrypt functions
 */
public class AESUtils extends BaseCrypto {

	private final static Logger logger = LoggerFactory.getLogger(AESUtils.class);
	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final int KEY_SIZE = 128;
	private static final String ENCRYPTION_TYPE = "AES/CBC/PKCS5Padding";

	
	private  byte[] secretKey = {105, -26, 103, 78, 116, 120, -19, -33, 102, -115, -30, -79, -95, 71, 90, 25};

	private  static AESUtils uniqueInstance = new AESUtils();
	
	public static AESUtils getInstance() {
		return uniqueInstance;
	}
	
    private static IvParameterSpec getIV(){
    	byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        return new IvParameterSpec(iv);
    }
    /**
	 * Generate the secrey key and return as a hex string
	 *
	 * @return Secret key
	 * @throws AESException AES exception
	 */
	public byte[] generateSecretKey() throws AESException {
		try {
			KeyGenerator  kgen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
			kgen.init(KEY_SIZE); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			byte[] secretKey1  = skey.getEncoded();
			return secretKey1;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error en generacion de llave ", e);
			throw new AESException(e);
		}
	}

	public  byte[] encrypt(String plainText,byte[] key) throws AESException {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, getIV());
			return cipher.doFinal(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		} catch (NoSuchPaddingException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		} catch (InvalidKeyException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		} catch (IllegalBlockSizeException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		} catch (BadPaddingException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error( "Error en encripcion AES ", e);
			throw new AESException(e);
		}
	}
	
	public  byte[] encrypt(String plainText) throws AESException {
		return encrypt(plainText, secretKey);
	}
	
	public  String encrypt(String plainText,String key) throws AESException, DecoderException {
		byte[] encrypt = encrypt(plainText, asByte(key));
		return asHex(encrypt);
	}

	
	public  byte[] decrypt(byte[] encrypted,byte[] key) throws AESException {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, getIV());
			return cipher.doFinal(encrypted);
		} catch (NoSuchAlgorithmException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		} catch (NoSuchPaddingException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		} catch (InvalidKeyException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		} catch (IllegalBlockSizeException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		} catch (BadPaddingException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error( "Error en desencripcion AES ", e);
			throw new AESException(e);
		}
	}

	public  byte[] decrypt(byte[] encrypted) throws AESException {
		return decrypt(encrypted, secretKey);
	}

	public  byte[] decrypt(byte[] encrypted,String key) throws AESException, DecoderException {
		return decrypt(encrypted, asByte(key));
	}
	
	public  String decrypt(String encrypted,String key) throws AESException, DecoderException, UnsupportedEncodingException {
		byte[] decrypt = decrypt(asByte(encrypted), asByte(key));
		return new String(decrypt, "UTF-8");
	}
	
	public String getSecretyKey() {
		return asHex(secretKey);
	}

	public byte[] getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}
	
    public static void main(String[] args) throws Exception {
//		String s = "{\"param\":{\"clientId\":\"c1ebe466-1cdc-4bd3-ab69-77c3561b9dee\",\"postHandleUrl\":\"http://www.htjf3.com/ssooklogin.action\",\"returnUrl\":\"http://www.htjf3.com/api/pc/product/baseList.action?productType=1\"}}";
//		
//		String encrypt = AESUtils.getInstance().encrypt(s, "a49610cf512fdad02c2396ad322f2307");
//		System.out.println(encrypt);
    	
    	byte[] generateSecretKey = AESUtils.getInstance().generateSecretKey();
    	String encode = Base64Util.encode(generateSecretKey);
    	System.out.println(encode);
	}
	
}
