package basic.arch.tools.utils;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

public class ParameterUtils {
	/**
	 * 获取页面传递来的String类型参数
	 * @param request
	 * @param inputStr
	 * @return
	 */
	public static String getString(HttpServletRequest request, String inputStr){
		String str = request.getParameter(inputStr);
		return isNull(str)?null:str;
	}
	
	/**
	 * 获取页面传递来的Integer类型参数
	 * @param request
	 * @param inputStr
	 * @return
	 */
	public static Integer getInteger(HttpServletRequest request, String inputStr){
		String str = request.getParameter(inputStr);
		Integer it = null;
		if(isNull(str))return null;
		try {
			it = Integer.valueOf(str);
		} catch (Exception e) {
			it = -1;
		}
		return it;
	}
	
	/**
	 * 获取页面传递过来的int类型参数
	 * @param request
	 * @param inputStr
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String inputStr){
		String str = request.getParameter(inputStr);
		int it = 0;
		if(isNull(str))return -1;
		try {
			it = Integer.valueOf(str).intValue();
		} catch (Exception e) {
			it = -1;
		}
		return it;
	}
	
	/**
	 * 获取页面传递过来的Long类型参数
	 * @param request
	 * @param inputStr
	 * @return
	 */
	public static Long getLong(HttpServletRequest request, String inputStr){
		String str = request.getParameter(inputStr);
		Long it = null;
		if(isNull(str))return null;
		try {
			it = Long.valueOf(str);
		} catch (Exception e) {
			it = -1l;
		}
		return it;
	}
	
	/**
	 * 获取页面传递过来的BigDecimal类型参数
	 * @param request
	 * @param inputStr
	 * @return
	 */
	public static BigDecimal getBigDecimal(HttpServletRequest request, String inputStr){
		String str = request.getParameter(inputStr);
		BigDecimal it = null;
		if(isNull(str))return null;
		try {
			it = new BigDecimal(str);
		} catch (Exception e) {
			it = new BigDecimal(-1l);
		}
		return it;
	}
	
	/**
	 * 获取页面传递过来的以id命名的参数
	 * @param request
	 * @return
	 */
	public static Long getId(HttpServletRequest request){
		String str = request.getParameter("id");
		Long it = null;
		if(isNull(str))return null;
		try {
			it = Long.valueOf(str);
		} catch (Exception e) {
			it = -1l;
		}
		return it;
	}
	
	private static boolean isNull(final Object xxx) {
		if (xxx == null) {
			return true;
		}
		if ("".equals(xxx.toString().trim())) {
			return true;
		}
		return false;
	}
}
