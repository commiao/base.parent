package basic.arch.tools.encrypt;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import basic.arch.tools.encrypt.exception.CipherRuntimeException;

@SuppressWarnings("restriction")
public class DescEncrypt {
    private static final String Algorithm = "DES"; // 定义 加密算法,可用

    // src为被加密的数据缓冲区（源）
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1);
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
            throw new RuntimeException(e2);
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
            throw new RuntimeException(e3);
        }
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            throw new CipherRuntimeException(e1);
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
            throw new CipherRuntimeException(e2);
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
            throw new CipherRuntimeException(e3);
        }
    }

    // 转换成十六进制字符串
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + "";
        }
        return hs.toUpperCase();
    }

    // 16 进制 转 2 进制
    private static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

  

    // 加密
   @SuppressWarnings("unused")
   private static byte[] encryptRetByte0(byte[] src, byte[] key) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        byte[] encrypt = encryptMode(key, src);
        return encrypt;
    }
   
   
   // 加密
   public  static String encrypt(String str, String key) {
       Security.addProvider(new com.sun.crypto.provider.SunJCE());
       byte[] encrypt = encryptMode(hex2byte(key), str.getBytes());
       return byte2hex(encrypt);
   }

    // 解密
    public static String decrypt(String str,String key) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        byte[] decrypt = decryptMode(hex2byte(key), hex2byte(str));
        return new String(decrypt);
    }
    


    public static void main(String arg[]) {
        String strKey = "1341567829433204"; //
        System.out.println(strKey.length());
        String url = "12121212";
        String url2 = encrypt(url,strKey);// 加密
        String url3 = decrypt(url2, strKey);// 加密
        System.out.println("url加密后:\n" + url2);
        System.out.println("url解密后:\n" + url3);
    }
}