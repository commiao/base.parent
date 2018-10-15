package basic.arch.tools.encrypt;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * CreateDate:2016年11月30日下午2:33:30 
 * @Description: base64编码工具类，使用commons-codec实现
 * @author:yuzhao
 * @version V1.0
 */
public class Base64ApacheUtil {
	
	//默认编码集
	private static final String ENCODING_DEFAULT = "UTF-8";
	
	/**
	 * 
	 * @Description::base64编码. <br/> 
	 * @param s
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s, String encoding) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(s.getBytes(encoding));
	}
	
	/**
	 * 
	 * @Description::base64编码. <br/> 
	 * @author yuzhao
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encoding(String s) throws UnsupportedEncodingException {
		return encode(s, ENCODING_DEFAULT);
	}
	
	/**
	 * 
	 * @Description::base64解码. <br/> 
	 * @author yuzhao
	 * @param s
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String s, String encoding) throws UnsupportedEncodingException {
		byte[] bytes = Base64.decodeBase64(s);
		return new String(bytes, encoding);
	}
	
	/**
	 * 
	 * @Description::base64解码. <br/> 
	 * @author yuzhao
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String s) throws UnsupportedEncodingException {
		return decode(s, ENCODING_DEFAULT);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String s = "src=http://www.chtfund.com/fundDetail.do?tradeFundCode=000001&str=恒天明泽公募产品交易网站";
//		String s1 = encoding(s);
//		System.out.println(s1);
//		String s2 = decode(s1);
//		System.out.println(s2);
		String str1 = "c3JjPWh0dHA6Ly93d3cuY2h0ZnVuZC5jb20vZnVuZERldGFpbC5kbz90cmFkZUZ1bmRDb2RlPTAwMDAwMSZzdHI95oGS5aSp5piO5rO95YWs5Yuf5Lqn5ZOB5Lqk5piT572R56uZJnRpY2tldD02Y2FlN2NhZGRkYzRhZjFmMmY1YWUxY2ZiMzA2Yjc5MDZlMGEzMWVkNzMwZGI0OTZmNjcxZDA3Mjk1NGI3ZDRjNjFhYmY4MjYyZTRkNjVkOWIwOGU5NTdiNWVjZjZhZjMmaHNpZD1jOTI2ZGQ3MzI3MDkzMDY4NTZjZWEyZDhlN2QxNTJjMw==";
//		String str = "c3JjPWh0dHA6Ly93d3cuY2h0ZnVuZC5jb20vZnVuZERldGFpbC5kbz90cmFkZUZ1bmRDb2RlPTAwMDAwMSZzdHI95oGS5aSp5piO5rO95YWs5Yuf5Lqn5ZOB5Lqk5piT572R56uZ";
		System.out.println(decode(str1));
	}

}
