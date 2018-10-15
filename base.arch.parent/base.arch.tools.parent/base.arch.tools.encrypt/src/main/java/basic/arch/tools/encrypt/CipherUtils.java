package basic.arch.tools.encrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

import basic.arch.tools.encrypt.exception.CipherRuntimeException;

/**
 * @author jannal
 */
public class CipherUtils {

    private static final String MD5 = "md5";

    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance(MD5).digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new CipherRuntimeException(e);
        }
         String md5Str = new BigInteger(1, secretBytes).toString(16);
         if(!StringUtils.isEmpty(md5Str)){
             int length = md5Str.length();
            //不够32位补0
             for (int i = 0; i < 32 -length; i++) {
                 md5Str = "0" + md5Str;
             }
         }
        return md5Str;
    }
    
    public static String SHA(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("SHA").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new CipherRuntimeException("没有SHA这个算法！",e);
        }
        String shaCode  =new BigInteger(1, secretBytes).toString(16);
        if(!StringUtils.isEmpty(shaCode)){
            int length = shaCode.length();
            for (int i = 0; i < 40 -length ; i++) {
                 shaCode = "0" + shaCode;
            }
        }else{
            throw new CipherRuntimeException("SHA加密出现问题");
        }
        return shaCode;

   }
}
