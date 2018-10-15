package basic.arch.tools.utils.common;

import java.io.UnsupportedEncodingException;

public class StringLengthUtil {
	public static void main(String[] args) {
		String a = "在互联网公司，哪有男妇之分-- 二环.";
		try {
			System.out.println("a = " + subStr(a, 10));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param str
	 * @param subSLength
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String subStr(String str, int subSLength)
			throws UnsupportedEncodingException {
		if (str == null) {
			return "";
		} else if(str.length() < subSLength){
		    return str;
		} else {
			int tempSubLength = subSLength;// 截取字节数
			String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);// 截取的子串
			int subStrByetsL = subStr.getBytes("UTF-8").length;// 截取子串的字节长度
			// int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
			// 说明截取的字符串中包含有汉字
			while (subStrByetsL > tempSubLength) {
				int subSLengthTemp = --subSLength;
				subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
				subStrByetsL = subStr.getBytes("UTF-8").length;
				// subStrByetsL = subStr.getBytes().length;
			}
			
			return subStr + "......";
		}
	}

	public static String getByteStr(String str, int start, int end)
			throws UnsupportedEncodingException {
		byte[] b = str.getBytes("UTF-8");

		return new String(b, start, end);
	}
}
