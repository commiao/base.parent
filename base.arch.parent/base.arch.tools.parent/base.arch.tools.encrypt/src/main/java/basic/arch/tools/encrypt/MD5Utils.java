package basic.arch.tools.encrypt;

import java.security.MessageDigest;

public class MD5Utils {
	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	/**
	 * 使用自定义char[]+md5加密
	 * @param s
	 * @return
	 */
	public final static String MD5Encode(String input) {
		String returnString = null;
		try {
			byte[] strTemp = input.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				/* byte 8位 不带符号 
				 * >>> 4 右移四位(不管byte0的类型 位移处补0) 
				 * 0xf 十六进制 
				 * 即 高四位清空 取低四位的值 >>>优先级高于 &
				 */
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			returnString = new String(str);
		} catch (Exception e) {
		}

		return returnString;
	}
	
	/**
	 * 使用十六进制转换+md5加密
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
				// 转换十六进制
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
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
}
