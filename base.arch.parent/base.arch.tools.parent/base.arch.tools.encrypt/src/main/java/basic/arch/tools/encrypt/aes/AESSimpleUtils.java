package basic.arch.tools.encrypt.aes;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import basic.arch.tools.encrypt.Base64Util;
import basic.arch.tools.encrypt.BaseCrypto;

/**
 * 
 * CreateDate:2016年11月22日下午5:14:56 
 * @Description: AES简单加密解密工具类
 * @author:yuzhao
 * @version V1.0
 */
public class AESSimpleUtils {
	
	private static final String ENCRYPTION_TYPE = "AES/ECB/PKCS5Padding";
	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final String ENCODE_TYPE = "utf-8";
	private static final int KEY_SIZE = 128;
	
	/**
	 * 
	 * @Description::生成密钥，base64编码. <br/> 
	 * @author yuzhao
	 * @return
	 * @throws Exception
	 */
	public static String generateSecretKey() throws Exception {
		KeyGenerator  kgen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
		kgen.init(KEY_SIZE); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] secretKey1  = skey.getEncoded();
		return Base64Util.encode(secretKey1);
	}
	
	/**
	 * 
	 * @Description::AES加密. <br/> 
	 * @author yuzhao
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64Util.decode(key), ENCRYPTION_ALGORITHM));
        byte[] bytes = cipher.doFinal(str.getBytes(ENCODE_TYPE));
//        return Base64Util.encode(bytes);
        return BaseCrypto.asHex(bytes);
    }

	/**
	 * 
	 * @Description::AES解密. <br/> 
	 * @author yuzhao
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64Util.decode(key), ENCRYPTION_ALGORITHM));
//        byte[] bytes = Base64Util.decode(str);
        byte[] bytes = BaseCrypto.asByte(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, ENCODE_TYPE);
    }
    
    public static String getUUID(){
    	return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
    
    public static void main(String[] args) throws Exception {
//    	String s = "7cad7a45fd93b73093eb1fab5c175ac85a2769e6d62e2b566f677ca90fb128c8fe7fe6355283a1f70b211ac1412f0cc53b8f247c196f4dc97b131b1b28dd84d1c8fab174f091302cf0d3de0e02e60ad8b63be41b29ec2fadc651eaedc7b825b325a441547e90aae8692617c689a76945d93d3b387d644c41342b70dd68f75c577234dcffb7ae5ec3a9d268a058c4f5620b817cc0a98f361ee8ab9b6d961b866bcfb9312549db1013c0a53baa049cf8df967263a13345b29a64ca5a5f9f1d7d6529eb267e254d349c6e807ad0712280c9a3065cd8e80d5f595bcabb17b308b4bcbaa2d8ad1f659bad78559cfcfaf1bca82787c90bca3c5c61b037e3962fcd59aeb32483ae0655583392a674a3d7c484d158a69df6e61c9ddcae758a4f8a3b92de76a7b9d8f96a06772bea368ed8e01c2389d98d1878fcebd8eb1c3eab1bd0376af7faaf01b50ba10e0927a66467105554d9d47d3175cd48949683f5ec22906551b17ee4dafe8e1e710bbfc98d764146b73877738dda88301e30dffbe500f9223371bf4a33b8cf8da0a454456ca532fe149956612759a704c7f7ba6752237ed427045d1d1c64e7e12a4c0b5f5f98eb34536ddb88436995c75bd63d1b46844dcea9ae8f8d707e43dd265dd21ff65b31023558f009405ac655bb9c955d2a1088c6f848002230c25290b76dac676a1fcec2e5e66fc3746e87dbe12d11bab1052b80f473468c12c622e536b5760fa93643aedd9a46f02db0f6cad5c90b14bf1f12e38782f79fa1bdf73682a13f588a6949fde7bbfe5c2bdade21f6d02af96780d76ea3abec333703d360cd6f03215854f1c9865938b4c98f561fdf9453557b7fbfa28e2c5d2ba977b399f5ed317e9b6b160b249e3baabde5d53463ac5f08bcf74e5ab754104e4d404282d1ab23fc6ffcaedf50cef28e222678bf7ddf38e53c3b32409436b07e916b876e9a268b0c821f05231fab6b36bedb073d3fe2c99b0b0c7380136672cf2dd505b51d9f1ee8204ad3cd0a9f35321ba3062b0d2fb3dbd00d9a17c1a95d7855003ac234e7cf90b0f93274cdae0352c4ee2a063e6bce96d99707656732108dd2621157e8cf4bc8eaf7fb3750b30e5a742da653764bca6cb230570c67e8a9cc5fbade067a771a9fa561ba49576ac97a74f69b5ce8bd3066b71b0d4fc1dc555f0deb017d961a5402ce1f5227901a300eba37a7fcc79f5989daa66d16ba08b044a84df39d2d26332dae3c407fb42b10c32b2eebfb3354bb3555ca5821b772e6bb32a6f341b68d0752177215b32349f40950e12b8d6f8b88327c5529893d0adf28e2bc7e25a40a193a214822b72ea309d12c29abc9757b17b90cf9c0e186ffbfec1beaaf8374d5c4a0fd99cbde52bea952525a54f7578f03cf01f4f83d77";
//    	String k = "Y2jKJAqx2iZzQI0wVUW2GA==";
//    	String aesDecrypt = aesDecrypt(s, k);
//    	System.out.println(aesDecrypt);
//    	
//    	String pp = aesEncrypt(s, k);
//    	System.out.println(pp);
//    	
//    	String ppp = aesDecrypt(pp, k);
//    	System.out.println(s);
//    	System.out.println(ppp);
    	
//    	String key = "Qsj9oRf10hG8lJpbrT1kbg==";
//    	String ticket = "HTCFTOMZ";
//    	String temp1 = getUUID();
//    	System.out.println("uuid："+temp1);
//    	temp1 += ticket;
//    	System.out.println("ticket明文："+temp1);
//    	String pp1 = aesEncrypt(temp1, key);
//    	System.out.println("ticket密文："+pp1);
    	
    	String key = "Qsj9oRf10hG8lJpbrT1kbg==";
		String ticket_ = "HTPEFPASSWORD";
		String ticket = getUUID()+ticket_;
		System.out.println("明文ticket："+ticket);
		String mi = aesEncrypt(ticket,key);
		System.out.println("密文ticket："+mi);
		
		
		System.out.println("解密："+AESSimpleUtils.aesDecrypt(mi, key).substring(32));
    	
//    	String pp2 = aesDecrypt(pp1, key);
//    	String temp2 = pp2.substring(32);
//    	System.out.println(temp2);
//    	
//    	if(ticket.equals(temp2)){
//    		System.out.println("成功!");
//    	}
    	
    	
//		String s = "8456700be1dd422598d404bcfddf3667_{\"param\":{\"clientId\":\"c1ebe466-1cdc-4bd3-ab69-77c3561b9dee\",\"postHandleUrl\":\"http://localhost:20265/Users_LoginPostHandleUrl.ashx\",\"returnUrl\":\"http://localhost:20265/Users_LoginReturnUrl.ashx\"}}";
//		String k = "VqwZ2seBhYX+4a8ymSaJQg==";
//		
//		String aesEncrypt = aesEncrypt(s, k);
//		System.out.println(aesEncrypt);
//		
//		String aesDecrypt = aesDecrypt(aesEncrypt, k);
//		System.out.println(aesDecrypt);
    	
//    	System.out.println(generateSecretKey());
	}
}