package basic.arch.tools.utils.common;

import java.util.Locale;

public class BigdataConstants {
    
    public final static Locale DEFAULT_LOCALE = Locale.CHINA;
    
    //默认分页数据条数
    public final static int PAGE_SIZE_DEFAULT = 20;
    //默认分页页码
    public final static int PAGE_NO_DEFAULT = 1;
    
    public final static int PAGE_SIZE_WEB = 14;
    public final static int PAGE_SIZE = 10;
    public final static int GINKGO_CONTENT_LENGTH = 260;// 银杏研究长度.
    // redis的key前缀.
    public final static String REDIS_KEY_PREFIX = "user:mail:";
    //默认系统异常code
    public final static String DEFAULT_SYSTEM_ERROR = "SF9999";

    /**
     * 图形验证码长度
     */
    public final static int IMAGE_CODE_LENGTH = 4;
    
    public static final String REGISTER_VALIDATE_CODE = "validateCode";
    
    public static final String REGISTER_VALIDATE_TYPE = "validateType";
    
	public final static String SESSION_USER_KEY = "session_user";
	
	public final static String SESSION_USER_KEY_POF = "session_user_pof";
	
	public final static String SESSION_PARAM_POFPEF_OFFER = "session_param_pofpef_offer";
	
	public final static String HEAD_INFO = "HEAD_INFO";
	
	//添加人员时默认值
	public final static String ADD_STAFF_DEFAULT_PASSWORD = "888888";
	
	//用户文件上传状态
	public final static String UPLOAD_USER_STATUS = "888888";
	
	/**
	 * 恒生接口返回成功标识
	 */
	public final static String SUCCESS_FLAG = "ETS-5BP0000";
	/**
	 * 恒生接口返回系统异常标识
	 */
	public final static String SYSTEM_ERROR = "ETS-5BP9999";
	
	/**
     * 恒生接口异常返回前端文本 
     */
    public final static String SYSTEM_ERROR_Text = "系统异常";
    
    /**
     * 小额理财结构标识
     */
    public final static String SUCCESS_SMALL = "1";
    /**  图片验证码  session 中存放标识   map  类型      */
    public final static String SESSION_IMG_CODE_TYPE = "SESSION_IMG_CODE_TYPE";
    /**
     * 验证码类型
     * 1：修改手机
        10:用户注册
        11：找回密码
        12:修改邮箱
        13:邮箱认证
     */
    /**   1：修改手机       */
    public final static String VERIFY_CODE_PHONE_1 = "1";
    /**   3：修改手机    原手机   */
    public final static String VERIFY_CODE_PHONE_3 = "3";
    /**   10:用户注册       */
    public final static String VERIFY_CODE_PHONE_10 = "10";
    /**    11：找回密码        */
    public final static String VERIFY_CODE_PHONE_11 = "11";
    /**    12:修改邮箱      */
    public final static String VERIFY_CODE_PHONE_12 = "12";
    /**    13:邮箱认证    */
    public final static String VERIFY_CODE_PHONE_13 = "13";
    //2016-5-31 shl 增加 开始
    /**14：实名认证*/
    public final static String VERIFY_CODE_PHONE_14 = "14";
    //2016-5-31 shl 增加 结束
    /**手机验证码 业务类型 15.产品赎回*/
    public final static String VERIFY_CODE_PHONE_15 = "15";
    
    
    
    /**   1：修改手机       */
    public final static String VERIFY_CODE_IMG_1 = "1";
    /**   2:登陆       */
    public final static String VERIFY_CODE_IMG_2 = "2";
    /**   10:用户注册       */
    public final static String VERIFY_CODE_IMG_10 = "10";
    /**    11：找回密码        */
    public final static String VERIFY_CODE_IMG_11 = "11";
    /**    12:修改邮箱      */
    public final static String VERIFY_CODE_IMG_12 = "12";
    /**    13:邮箱认证    */
    public final static String VERIFY_CODE_IMG_13 = "13";
    /**    14:积分转赠    */
    public final static String VERIFY_CODE_IMG_14 = "14";
    
    // 注册.
    public final static String USER_MSG_REGIST = "regist"; 
    public final static String USER_MSG_REGIST_TITLE = "注册账户";
    public final static String USER_MSG_REGIST_INFO = "恭喜！您已成功注册为恒天金服用户！请先进行风险测评，以便帮您确认自己的风险识别能力和风险承担能力。";
    // 忘记密码修改成功.
    public final static String USER_MSG_FORPASS = "forpass";
    public final static String USER_MSG_FORPASS_TITLE = "找回登录密码";
    public final static String USER_MSG_FORPASS_INFO = "恭喜！您已成功找回密码！请妥善保管。";
    // 修改密码成功.
    public final static String USER_MSG_UPPASS = "uppass";
    public final static String USER_MSG_UPPASS_TITLE = "修改密码";
    public final static String USER_MSG_UPPASS_INFO = "恭喜！您已成功修改密码！建议定期更换，可提高账户的安全性。";
    // 增加或者修改邮箱成功.
    public final static String USER_MSG_UPEMAIL = "upemail";
    public final static String USER_MSG_UPEMAIL_TITLE = "设置邮箱";
    public final static String USER_MSG_UPEMAIL_INFO = "恭喜！您已成功完成邮箱认证！如常用邮箱有变动，请及时更换。";
    // 增加通讯地址成功.
    public final static String USER_MSG_UPADDRESS = "upaddress";
    public final static String USER_MSG_UPADDRESS_TITLE = "设置通讯地址";
    public final static String USER_MSG_UPADDRESS_INFO = "恭喜！您已成功设置通讯地址！如常驻地址有变动，请及时更新。";
    // 预约产品成功.
    public final static String USER_MSG_APPPRODUCT = "appproduct";
    public final static String USER_MSG_APPPRODUCT_TITLE = "预约产品";
    public final static String USER_MSG_APPPRODUCT_INFO = "您预约的*申请已成功提交！请保持电话畅通，理财师将会与您联系并沟通后续事宜。";
    // 预约活动成功.
    public final static String USER_MSG_APPACTIVITY = "appactivity";
    public final static String USER_MSG_APPACTIVITY_TITLE = "预约活动";
    public final static String USER_MSG_APPACTIVITY_INFO = "预约活动成功，请知晓！";
    // 绑定银行卡成功.
    public final static String USER_MSG_TIEDCARD = "tiedcard";
    public final static String USER_MSG_TIEDCARD_TITLE = "设置银行卡";
    public final static String USER_MSG_TIEDCARD_INFO = "设置银行卡成功，请知晓！";
    // 转赠积分成功.
    public final static String USER_MSG_TURNCRED = "turncred";
    public final static String USER_MSG_TURNCRED_TITLE = "转赠积分";
    public final static String USER_MSG_TURNCRED_INFO = "转赠积分成功，请知晓！";
    // 实名认证.
    public final static String USER_MSG_CERTIFICATION = "certification";
    public final static String USER_MSG_CERTIFICATION_TITLE = "实名认证";
    public final static String USER_MSG_CERTIFICATION_INFO = "恭喜！您已成功完成实名认证！可前往预约与您风险识别能力和风险承担能力相匹配的私募产品。";
    // 风险测评.
    public final static String USER_MSG_APPRAISAL = "appraisal";
    public final static String USER_MSG_APPRAISAL_TITLE = "风险测评";
    public final static String USER_MSG_APPRAISAL_INFO = "恭喜！您已成功完成风险测评！可前往查看私募产品。";  
    // 理财师指定.
    public final static String USER_MSG_APPOINTBROKER = "appointbroker";
    public final static String USER_MSG_APPOINTBROKER_TITLE = "理财师指定";
    public final static String USER_MSG_APPOINTBROKER_INFO = "恭喜！您已成功指定理财师！如有疑问，可联系专属理财师为您服务。";  
//  预约成功文案：您预约的***申请已提交！
//  用户注册成功文案：恭喜！您已成功注册为恒天金服用户！
//  实名认证成功文案：恭喜！您已成功实名认证！
//  找回密码成功文案：恭喜！您已成功找回密码！
//  修改密码成功文案：恭喜！您已成功修改密码！
//  设置通讯地址成功文案：恭喜！您已成功设置通讯地址！
//  风险测评成功文案：恭喜！您已成功完成风险测评！
//  理财师指定成功文案：恭喜！您已成功指定理财师！
//  邮箱认证成功文案：恭喜！您已成功完成邮箱认证！
    
    /**
     *  app  更新  查询类型 定义 
     */
    /** ios  */
    public final static String APP_UPDATE_QUERY_IOS = "0";
    /** android  */
    public final static String APP_UPDATE_QUERY_ANDROID = "1";
    
    
    
    /** 非第一次登陆  校验验证码   */
    public final static String LOGIN_SECOND_IMG_CODE = "LOGIN_SECOND_IMG_CODE";
    
    /**实名认证-系统来源 1.PC*/
    public final static String CERTIFICATION_SYSTEM_FROM_PC = "1";
    /**实名认证-系统来源 5.APP*/
    public final static String CERTIFICATION_SYSTEM_FROM_APP = "5";
    /**实名认证-系统来源 3.微信*/
    public final static String CERTIFICATION_SYSTEM_FROM_WECHAT = "3";
    
    /**系统来源*/
    public final static String CERTIFICATION_SYSTEM_FROM = "1";
    /**是否已经实名认证 1.否*/
    public final static String CERTIFICATION_FLAG_NO = "1";
    
    /**是否已经实名认证 2.是*/
    public final static String CERTIFICATION_FLAG_YES = "2";
    /**恒生理财师工号 状态  1.已离职*/
    public final static String HS_BROKEN_ACCOUNTNO_STATUS = "1";
    /**恒生接口 产品预约 功能标志 01认购*/
    public final static String HS_FUNCTION_FLAG_SUBSCRIPTION = "01";
    /**恒生接口 产品预约方式 1.金服*/
    public final static String HS_PRODUCT_RESERVE_ORDER_TYPE = "1";
    
    /**app注册 理财师不存在 1.离职*/
    public final static String APP_FINANCIAL_STATUS_LEAVE = "1";
    /**app注册 理财师不存在 2.锁定*/
    public final static String APP_FINANCIAL_STATUS_CLOCK = "2";
    /**app注册 理财师存在 0.正常 */
    public final static String APP_FINANCIAL_STATUS_OK = "0";
    /**app注册 4.理财师不存在*/
    public final static String APP_FINANCIAL_ISEXSIT_NO = "4";
    /**理财师不存在或已离职 返回前端code=5*/
    public final static String FINANCIAL_ISEXSIT_NO_CODE = "5";
    
    /**理财师锁定 返回前端code=6*/
    public final static String FINANCIAL_ISEXSIT_CLOCK_CODE = "6";
    
    /**恒天财富理财师不允许被绑定 返回前端code=7*/
    public final static String FINANCIAL_ISNO_BINDING_CODE = "7";

    
    /**电子合同返回状态-失败*/
    public final static String ELECTRONIC_CONTRACT_RET_CODE_ERROR = "9999";
    /**电子合同获取验证码失败 - 预约失败，请您稍后再试*/
    public final static String ELECTRONIC_CONTRACT_RET_MSG_ERROR = "预约失败，请您稍后再试";
    /**电子合同返回状态-成功**/
    public final static String ELECTRONIC_CONTRACT_RET_CODE_SUCCESS = "0000";
    /**金服后台是否启动  1.启用*/
    public final static int JF_ISENABLES_ENABLES = 1;
    
    /**
     * 理财师
     * Create Author: wrx
     * CreateDate:2016年9月22日上午9:46:27 
     * @Description: 理财师的一些常量 
     * @author:wrx
     * @version V1.0
     */
    public static class FinancialPlannerConstant{
        //离职
        public static final String FP_LZ_TYPE_CODE = "BF0001";
        //锁定
        public static final String FP_SD_TYPE_CODE = "BF0002";
        //不存在
        public static final String FP_NE_TYPE_CODE = "BF0003";
        //金服理财师不允许被绑定
        public static final String FP_NB_TYPE_CODE = "BF0004";
        
        //提示文案：无效或离职
        public static final String FP_HINT_LZ="您搜索的理财师工号不存在，请确认您填写的信息正确无误。";
        //提示文案：锁定、恒天明泽非持证
        public static final String FP_HINT_SD="您搜索的理财师工号无效，请联系您的理财师进行后续财富管理服务咨询。";
        //提示文案：恒天财富员工
        public static final String FP_HINT_HTCF="您搜索的理财师工号无效，请联系您的理财师进行后续财富管理服务咨询。";
        /**是否为专属理财师 1.是*/
        public static final String BROKER_IS_MAIN_YES = "1";
    }
    
    /**
     * 理财师文案
     * @author wrx
     *
     */
    public static class FinancialPlannerText{
        //注册提示文案：无效或离职
        public static final String FP_REGIST_LZ="您搜索的理财师工号不存在，请确认您填写的信息正确无误。";
        //注册提示文案：锁定、恒天明泽非持证
        public static final String FP_REGIST_SD="您搜索的理财师工号无效，请联系您的理财师进行后续财富管理服务咨询。";
        //注册提示文案：恒天财富员工
        public static final String FP_REGIST_HTCF="您搜索的理财师工号无效，请联系您的理财师进行后续财富管理服务咨询。";
        
        //预约、个人中心提示文案：无效或离职
        public static final String FP_COMMON_LZ="该理财师工号不存在，请重新确认";
        //预约、个人中心提示文案：锁定、恒天明泽非持证
        public static final String FP_COMMON_SD="该理财师工号线上无法指定，请线下联系理财师进行财富管理服务";
        //预约、个人中心提示文案：恒天财富员工
        public static final String FP_COMMON_HTCF="该理财师工号为恒天财富员工，请线下联系理财师进行财富管理服务";
        
        //如果客户有专属的北京恒天财富理财师，则用户输入任何理财师工号，返回文案
        public static final String FP_FIND_MAIN_HTCF="您已指定恒天财富理财师，如需变更请联系理财师或客服！";
        
        //二次解绑提示
        public static final String FP_JB_TEXT="您已变更过专属理财师，再次变更需满半年才能操作。";
    }
    
    //=================用户信息使用常量begin====================
    public static class UserinfoForAccoutConstant{
        /**是否绑定联系人手机 1.否*/
        public final static String IS_BIND_PHONE_NO = "1";
        /**是否绑定联系人手机 2.是*/
        public final static String IS_BIND_PHONE_YES = "2";
        /**是否实名认证 1.否*/
        public final static String IS_CERTIFICATION_NO = "1";
        /**是否实名认证 2.是*/
        public final static String IS_CERTIFICATION_YES = "2";
        /**是否风险测评 1.否*/
        public final static String IS_RISK_APPRAISAL_NO = "1";
        /**是否风险测评 2.是*/
        public final static String IS_RISK_APPRAISAL_YES = "2";
        /**用户信息查询异常*/
        public final static String USER_INFO_CODE_QUERY_ERROR = "UF0001";
        
        /**用户所属公司前缀编码 00010001北京恒天明泽00010002北京恒天财富 */
        public static final String DEPT_NODE_CODE_CF="00010002";
           
        public static final String DEPT_NODE_CODE_MZ="00010001";
        /**账户类型  1.客户编号*/
        public static final String HS_ACCO_TYPE_CUST_NO = "1";
        /**客户资料修改-客户标识类别  默认1*/
        public static final String HS_CLIENT_FLAG_TYPE = "1";
        /**客户资料修改-功能编号  默认83*/
        public static final String HS_FUNCTION_FLAG = "83";
        /**客户资料修改-操作来源  默认5*/
        public static final String HS_OPERATOR_FROM = "5";
        /**密码生成方式 1.用户修改、指定的密码*/
        public static final String HS_PASSWORD_FROM_ASSIGN = "1";
        
    }
    //=================用户信息使用常量end====================
    
    //=======赎回业务功能使用常量 begin=======
    public static class RedemptionConstant{
    	/**金服中  是否可赎回 2.是*/
    	public static final String JF_IS_ALLOW_REDEMPTION_YES = "2";
      	/**金服中  是否可赎回 1.否*/
    	public static final String JF_IS_ALLOW_REDEMPTION_NO = "1";
    	/**恒生工作标识 1.工作日*/
    	public static final String HS_WORK_DATE_FLAG_NORMAL = "1";
    	/**银行工作标识 1.工作日*/
    	public static final String BANK_WORK_DATE_FLAG_NORMAL = "1";
    	/**新增产品赎回申请失败*/
    	public static final String JF_ADD_REDEMPTION_ERROR_CODE = "RF0001";
    	/**新增产品赎回申请返回为空*/
    	public static final String JF_ADD_REDEMPTION_RET_NULL = "RF9999";
    	/**查询当前份额失败*/
    	public static final String JF_QUERY_SHARE_ERROR_CODE = "QF0001";
    	/**当前份额为空*/
    	public static final String JF_QUERY_SHARE_NULL_CODE = "QF0002";
    	/**查询赎回申请失败*/
    	public static final String JF_QUERY_REDEMPTION_ERROR_CODE = "QF0003";
    	/**产品赎回   当天起始时间*/
//    	public static final String JF_REDEMPTION_TIME_START = "09:00:00";
    	/**产品赎回   当天结束时间*/
//    	public static final String JF_REDEMPTION_TIME_END = "15:00:00";
    	/**产品赎回申请信息查询-申请状态 2.审核通过*/
    	public static final String HS_REDEMPTION_INFO_ING = "2";
    	/**产品赎回申请信息查询-申请状态 4.已撤销*/
    	public static final String HS_REDEMPTION_INFO_REPEAL = "4";
    	/**产品赎回申请信息查询-申请状态 6.已确认*/
    	public static final String HS_REDEMPTION_INFO_CONFIRMED = "6";
    	/**赎回申请-支付时间T+1 则传1*/
    	public static final String HS_REDEMPTION_PAY_DAYS = "1";
    	
    }
    //=======赎回业务功能使用常量 end=======
    
    //=========私募鉴权实名认证使用常量 begin=========
    public static class PefAuthConstant{
    	
    	public static final String JF_PEF_AUTH_NULL = "参数对象为空";
    	
    	public static final String JF_PEF_AUTH_STEPID_ISNULL="鉴权步骤id不能为空";
    	
    	public static final String JF_PEF_AUTH_BANKNO_ISNULL = "银行卡号不能为空"; 
    	
    	public static final String JF_PEF_AUTH_BANKIDNO_ISNULL = "发卡银行不能为空";
    	
    	public static final String JF_PEF_AUTH_BRANCHNO_ISNULL = "联行号不能为空";
    	
    	public static final String JF_PEF_AUTH_CUSTIDNO_ISNULL = "证件号码不能为空";
    	
    	public static final String JF_PEF_AUTH_CUSTIDTYPE_ISNULL = "证件类型不能为空";
    	
    	public static final String JF_PEF_AUTH_CUSTNAME_ISNULL = "姓名不能为空";
    	
    	public static final String JF_PEF_AUTH_IMAGCODE_ISNULL = "图文验证码不能为空";
    	
    	public static final String JF_PEF_AUTH_MOBELNO_ISNULL = "手机号码不能为空";
    	
    	public static final String JF_PEF_AUTH_SMSCODE_ISNULL = "短信验证码不能为空";
    	
    	public static final String JF_PEF_AUTH_ORIGINALSERIALNO_ISNULL = "原始流水号不能为空";
    	/**鉴权第一步*/
    	public static final String JF_PEF_AUTH_STEP_FIRST = "1";
      	/**鉴权第二步*/
    	public static final String JF_PEF_AUTH_STEP_SECOND = "2";
    	/**私募第一步鉴权流水号 存入session中的key*/
    	public static final String JF_PEF_AUTH_SERIALNO_SESSION_KEY = "pef_auth_serialNo";
    	
    }
    //=========私募鉴权实名认证使用常量 end=========
    
    //====================证件类型（公募、私募）begin============
    public static class CertTypeConstant{
    	/**公募 身份证0*/
    	public static final String POF_CERT_TYPE_IDCARD = "0";
    	/**公募 外国护照1*/
    	public static final String POF_CERT_TYPE_FOREIGNPASSPORT = "1";
    	/**公募 回乡证10*/
    	public static final String POF_CERT_TYPE_HOMEVISITPERMIT = "10";
    	/**公募 台胞证11*/
    	public static final String POF_CERT_TYPE_TAIWANPERMIT = "11";
    	/**恒生 身份证0*/
    	public static final String HS_CERT_TYPE_IDCARD = "0";
    	/**恒生 外国护照6*/
    	public static final String HS_CERT_TYPE_FOREIGNPASSPORT = "6";
       	/**恒生 回乡证4*/
    	public static final String HS_CERT_TYPE_HOMEVISITPERMIT = "4";
    	/**恒生 台胞证D*/
    	public static final String HS_CERT_TYPE_TAIWANPERMIT = "D";
    }
    //====================证件类型（公募、私募）begin============
    
    //加密类相关常量
    public static class AesConstant{
        //aes加密key
        public static final String ENCRYPT_PASSWORD_AESKEY="Qsj9oRf10hG8lJpbrT1kbg==";
        //aes加密ticket
        public static final String ENCRYPT_PASSWORD_TICKET="htpefpassword";
        //session中的密码key
        public static final String SESSION_PASSWORD_KEY="session_password_key";
        //aes私募跳转公募加密ticket
        public static final String PEF_GOTO_POF_TICKET="HTCFTOMZ";
        //aes私募跳转公募加密key
        public static final String PEF_GOTO_POF_KEY="Qsj9oRf10hG8lJpbrT1kbg==";
    }
    
	/**
	 * 
	 * CreateDate:2016年9月28日下午11:57:28 
	 * @Description: http请求响应状态
	 * @author:yuzhao
	 * @version V1.0
	 */
	public static class ResponseStatus {
		public static final int RESPONSE_STATUS_200 = 200;
		public static final int RESPONSE_STATUS_302 = 302;
		public static final int RESPONSE_STATUS_400 = 400;
		public static final int RESPONSE_STATUS_404 = 404;
		public static final int RESPOSNE_STATUS_500 = 500;
    }
	
	/**
	 * 
	 * CreateDate:2016年12月28日上午9:33:35 
	 * @Description: 产品信息常量  
	 * @author:shl
	 * @version V1.0
	 */
	public static class ProductInfoConstant{
		/**属于恒天财富部门*/
		public static final String RISK_FLAG_TWO = "2";
		/**用户风险级别符合产品级别*/
		public static final String RISK_FLAG_ZERO = "0";
		/**用户风险级别不符合产品级别*/
		public static final String RISK_FLAG_ONE = "1";
		/**产品状态 1.您尚未预约该产品，请预约。*/
		public static final String PRODUCT_STATUS_ONE = "1";
		/**产品状态 2.请联系您的理财顾问，预约该产品。*/
		public static final String PRODUCT_STATUS_TWO = "2";
		/**产品状态 3.您选择的产品与您现在的风险承受能力不匹配。如确认预约此产品，请重新评测。*/
		public static final String PRODUCT_STATUS_THREE = "3";
		/**产品状态 4.该产品已售罄，请预约其他产品*/
		public static final String PRODUCT_STATUS_FOUR = "4";
		/**产品状态 5.产品已成立*/
		public static final String PRODUCT_STATUS_FIVE = "5";
		/**产品状态 6.您已经预约产品*/
		public static final String PRODUCT_STATUS_SIX = "6";
		/**产品状态 7.产品即将开始，请耐心等待或预约其他产品*/
		public static final String PRODUCT_STATUS_SEVEN = "7";
		/**2.类固收*/
		public static final String PRODUCT_BONUS_TYPE_FIXED = "2";
		/**3.浮收*/
		public static final String PRODUCT_BONUS_TYPE_FLOAT = "3";
		/**净值是否公布 1*/
		public static final String NET_VALUE_PUBLISH_FLAG = "1";
		/**风险提示书 标识*/
		public static final String RISK_REMINDER_CATALOG = "TFUNDINFO";
		/**产品预约状态存session key*/
		public static final String RESERVE_PRODUCT_SESSION_KEY = "product_reserve";
		/**产品状态：非正常状态*/
		public static final String PRODUCT_STATUS_UNNORMAL = "PF9980";
		/**产品预约状态 1.已撤销*/
		public static final String PRODUCT_ORDER_STATUS_ONE = "1";
		/**产品预约状态 0.排队中*/
		public static final String PRODUCT_ORDER_STATUS_ZERO = "0";
		/**产品预约状态 7.排队成功*/
		public static final String PRODUCT_ORDER_STATUS_SEVEN = "7";
		/**产品预约状态 f.待确认*/
		public static final String PRODUCT_ORDER_STATUS_F = "f";
		/**营销项目状态 1.待发行*/
		public static final String PRODUCT_SALE_PRO_STATUS_ONE = "1";
		/**营销项目状态 2.发行中*/
		public static final String PRODUCT_SALE_PRO_STATUS_TWO = "2";
		/**营销项目状态 3.发行结束*/
		public static final String PRODUCT_SALE_PRO_STATUS_THREE = "3";
		/**产品专题 公私募产品标识 privatePlacement表示私募**/
		public static final String PRODUCT_PEFORPOF_PEF = "privatePlacement";
		/**产品专题 公私募产品标识 publicOffering表示公募**/
		public static final String PRODUCT_PEFORPOF_POF = "publicOffering";
	}
	
	/**
	 * 
	 * CreateDate:2017年1月4日下午4:25:25 
	 * @Description: 人脸识别使用常量  
	 * @author:shl
	 * @version V1.0
	 */
	public static class FaceIdentifyConstant{
		/**图像保存类型 1.OCR*/
		public static final String IMG_TYPE_OCR = "1";
		/**图像保存类型 2.活体图像*/
		public static final String IMG_TYPE_LIVING = "2";
		/**图像保存类型 3.公安部截图*/
		public static final String IMG_TYPE_POLICY = "3";
		/**文件保存限制大小 2M*/
		public static final long IMG_LENGTH_MAX = 1024*1024*2;
		
	}
	
	/**
	 * 
	 * CreateDate:2017年1月5日下午6:50:51 
	 * @Description: 文件常量  
	 * @author:shl
	 * @version V1.0
	 */
	public static class FilesConstant{
		/**是否下载  1.是*/
		public static final String FILE_IS_DOWNLOAD_YES = "1";
		/**是否下载  2.否*/
		public static final String FILE_IS_DOWNLOAD_NO = "2";
	}
	
	    
    /**
     * 微信常量
     * Create Author: 程珑
     * CreateDate:2016年12月22日
     * @Description: 微信相关常量
     * @author:chenglong
     * @version V1.0
     */
    public static class WechatConstant{
        //微信授权返回code无效
        public static final String ILLEGAL_CODE_MSG = "微信返回code为空或无效";
        public static final String ILLEGAL_CODE_CODE = "WF0001";
        //微信授权code查询openId失败
        public static final String GET_OPENID_FAIL_MSG = "微信授权code查询openId失败";
        public static final String GET_OPENID_FAIL_CODE = "WF0002";
        //SalesForce接口调用异常
        public static final String SF_ERROR_MSG = "SalesForce接口调用异常";
        public static final String SF_ERROR_CODE = "WF0003";
        //SalesForce中没有对应的客户
        public static final String SF_NOUSER_MSG = "SalesForce中没有对应的客户";
        public static final String SF_NOUSER_CODE = "WF0004";
        //微信绑定SalesForce客户失败
        public static final String SF_BINDACCOUNT_FAIL_MSG = "微信绑定SalesForce客户失败";
        public static final String SF_BINDACCOUNT_FAIL_CODE = "WF0005";
        //多金账户登录失败       
        public static final String LOGIN_FAIL_MSG = "用户名或密码错误";
        public static final String LOGIN_FAIL_CODE = "WF0006";
        //恒生接口调用异常
        public static final String HS_ERROR_MSG = "恒生接口调用异常";
        public static final String HS_ERROR_CODE = "WF0007";

        //微信或SalesForce账号为空
        public static final String OPENID_OR_CLIENTID_EMPTY_MSG = "微信或SalesForce账号为空";
        public static final String OPENID_OR_CLIENTID_EMPTY_CODE = "WF0008";
        //Redis服务异常
        public static final String REDIS_ERROR_MSG = "Redis服务异常";
        public static final String REDIS_ERROR_CODE = "WF0009";
        //Redis查询wechatId返回空
        public static final String REDIS_UNFIND_MSG = "Redis查询wechatId返回空";
        public static final String REDIS_UNFIND_CODE = "WF0010";
        public static final String NEED_LOGIN_TIP = "您的帐号已过期，请重新登录！";
        //短信验证码不正确
        public static final String VERIFY_ERROR_MSG = "短信验证码不正确";
        public static final String VERIFY_ERROR_CODE = "WF0011";
        public static final String VERIFY_ERROR_TIP = "验证码输入不正确，请重新输入";
        //Redis查询userInfo返回空
        public static final String REDIS_UNFIND_USERINFO_MSG = "Redis查询userInfo返回空";
        public static final String REDIS_UNFIND_USERINFO_CODE = "WF0012";
        public static final String NEED_LOGIN_USERINFO_TIP = "您的帐号已过期，请重新登录！";
        //恒生接口返回错误
        public static final String HS_SBP9999_MSG = "恒生接口返回错误";
        public static final String HS_SBP9999_CODE = "WF0013";
        //此微信openId查询SalesForce已绑定其他多金clientId
        public static final String LOGIN_BINDACCOUNT_FAIL_MSG = "此微信openId查询SalesForce已绑定其他多金clientId";
        public static final String LOGIN_BINDACCOUNT_FAIL_CODE = "WF0014";
        //此多金clientId被其他微信openId绑定
        public static final String LOGIN_CLIENT_BINDED_MSG = "此多金clientId被其他微信openId绑定";
        public static final String LOGIN_CLIENT_BINDED_CODE = "WF0015";
        //参数uuid不能为空
        public static final String WX_UUID_EMPTY_MSG = "页面session中uuid为空";
        public static final String WX_UUID_EMPTY_CODE = "WF0016";
        //参数openId不能为空
        public static final String WX_OPENID_EMPTY_MSG = "页面中的openId不能为空";
        public static final String WX_OPENID_EMPTY_CODE = "WF0017";
        //检测到此微信已登录过金服账号，请手动退出原登录账号后再注册金服账号
        public static final String WX_CANNOT_REGIST_MSG = "检测到此微信已登录过金服账号，请手动退出原登录账号后再注册金服账号";
        public static final String WX_CANNOT_REGIST_CODE = "WF0018";
        
        //系统异常
        public static final String SYSTEM_ERROR_MSG = "系统异常";
        public static final String SYSTEM_ERROR_CODE = "WF9999";
        //忘记密码发送至联系人手机号
        public static final String SEND_TO_CONTACT_PHONE = "0";
        
        //产品预约状态值
        /**排队中*/
        public static final String HS_ORDER_STATUS_QUEUEING = "0";
        
        /**排队成功*/
        public static final String HS_ORDER_STATUS_QUEUED = "7";
        /**待确认*/
        public static final String HS_ORDER_STATUS_DETERMINED = "f";
        
        /**是否取消1.否*/
        public static final String IS_CANCEL_NO = "1";
        /**是否取消2.是*/
        public static final String IS_CANCEL_YES = "2";
        /**多金接口 异常*/
        public static final String HS_SERVICE_ERROR_MSG = "服务器异常，请稍后重试";
        /**多金接口 异常code：WF9998*/
        public static final String HS_SERVICE_ERROR_CODE = "WF9998";
    }
    
    /**
     * 
     * CreateDate:2017年3月3日上午9:34:19 
     * @Description: wap常量  
     * @author:shl
     * @version V1.0
     */
    public static class WapConstants{
    	/**恒生标识类别 D:手机号码*/
    	public static final String HS_ACCO_TYPE_D = "D";
    	/**操作来源 6:WAP*/
    	public static final String HS_OPERATOR_FROM_WAP = "6";
    	/**登录校验 用户名或密码错误*/
    	public static final String LOGIN_CHECK_ACCOUNT_NO_EXSIT = "UF0001";
    	/**恒生登录校验 1.密码错误*/
    	public static final String HS_LOGIN_CHECK_PWD_ERROR = "1";
     	/**恒生登录校验 2.密码主动锁定*/
    	public static final String HS_LOGIN_CHECK_PWD_LOCK = "2";
     	/**恒生登录校验 4.密码无效*/
    	public static final String HS_LOGIN_CHECK_PWD_INVALID = "4";
    	/**恒生登录校验 5.主动锁*/
    	public static final String HS_LOGIN_CHECK_PWD_ACCORDLOCK = "5";
    	/**密码重置 功能号 默认值02*/
    	public static final String HS_BUSIN_FLAG = "02";
    	/**密码重置 重置原因 默认值1.密码挂失重置*/
    	public static final String HS_RESET_REASON = "1";
    	/**产品收益分配 2.类固收*/
    	public static final String HS_BONUS_TYPE_STABILIZATION = "类固收";
    	/**产品收益分配 2.类固收*/
    	public static final String HS_BONUS_TYPE_DIC_STABILIZATION = "2";
    	/**产品收益分配 3.浮收*/
    	public static final String HS_BONUS_TYPE_FLOAT = "浮收";
     	/**产品收益分配 3.浮收*/
    	public static final String HS_BONUS_TYPE_DIC_FLOAT = "3";
    	/**产品收益分配 1.实际收益*/
    	public static final String HS_BONUS_TYPE_ACTUAL = "实际收益";
    	/**产品收益分配 1.实际收益*/
    	public static final String HS_BONUS_TYPE_DIC_ACTUAL = "1";
    	/**首次登录标识 1.首次登录*/
    	public static final String HS_FIRST_LOGIN_FIRST = "1";
    	/**私募密码结果 0.成功*/
    	public static final String HS_MODIFY_RESULT_SUCCESS = "0";
    	/**私募密码结果 5.新密码不符合设定的规则*/
    	public static final String HS_MODIFY_RESULT_NO_REGULATION = "5";
    	/**修改密码错误提示语-原登录密码错误*/
    	public static final String MODIFY_PWD_ERROR_MSG = "原密码输入不正确，请重新输入";
     	/**修改密码错误提示语-新密码不符合设定的规则*/
    	public static final String MODIFY_NEW_PWD_ERROR_MSG = "新密码不符合设定的规则";
    	/**产品预约重复提交*/
    	public static final String PRODUCT_RESERVE_RESUBMIT = "PF9999";
    		
    }
	/**
	 * @author wrx
	 */
	public class PofAccount{
    	/**公私募账户已合并*/
    	public static final String HS_IS_HAS_CLIENTID_YES = "0";
    	/**公募帐户未开户 */
    	public static final String HS_IS_HAS_CLIENTID_NO = "1";
    	/**公募帐户已开户但未合并  2*/
    	public static final String HS_IS_HA_CLIENTID_OPPENACCOUNT="2";
    	/**公募帐户信息查询异常*/
    	public static final String HS_IS_HA_CLIENTID_EXCEPTION="3";
	}
    
    
}
