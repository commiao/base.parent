package basic.arch.tools.encrypt.rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * CreateDate:2016年9月29日上午2:07:58 
 * @Description: RSA密钥生成器
 * @author:yuzhao
 * @version V1.0
 */
@SuppressWarnings({ "unused", "restriction" })
public class RSAGenerater {
	
//	private final static Logger LOGGER = LoggerFactory.getLogger(RSAGenerater.class);
	
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	public static void main(String[] args) {
		Map<String, Object> keyMap;
		try {
			keyMap = initKey();
			String publicKey = generatePublicKey(keyMap);
			System.out.println("publicKey : " + publicKey);
//			LOGGER.info("publicKey : {}", publicKey);
			String privateKey = generatePrivateKey(keyMap);
			System.out.println("privateKey : " + privateKey);
//			LOGGER.info("privateKey : {}", privateKey);
		} catch (Exception e) {
			e.printStackTrace();
//			LOGGER.error("密钥生成失败", e);
		}
	}

	/**
	 * 
	 * @Description::生成公钥. <br/> 
	 * @author yuzhao
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String generatePublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		byte[] publicKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 
	 * @Description::生成私钥. <br/> 
	 * @author yuzhao
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String generatePrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		byte[] privateKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 
	 * @Description::base64解密. <br/> 
	 * @author yuzhao
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * 
	 * @Description::base64加密. <br/> 
	 * @author yuzhao
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 
	 * @Description::初始化密钥生成算法. <br/> 
	 * @author yuzhao
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}