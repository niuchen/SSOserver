package com.SSOserver.common;

/**
 * API
 * @author sunjuncai
 * @date 2014-9-27
 */
public class API {
	
	/** 提示信息 */
	public static final class MSG{
		
		/** C类返回结果时提示信息  */
		public static final class C{
			public static final String NOT_SUCCESS = "获取数据失败";
			public static final String LOGIN_SUCCESS = "登录成功";
			public static final String NO_MODULE_POWER = "没有模块操作权限,请联系管理员";
			public static final String LOGIN_FAIL = "登录失败,请检查用户名和密码";
			public static final String LOGIN_LOCK = "登录失败,当前账号已在其他设备上登录";
			public static final String DATA_EMPTY = "获取数据为空";
			public static final String PARAMS_EMPTY = "参数不能为空";
			public static final String VERSION_FAIL = "获取版本信息失败";
		}
	}
	
	public static final class KEY{
		/** 密钥 */
		public static final String DES_CODE = "cn.tieta";
		/** 用户名 */
		public static final String USER_NAME = "v_login_name";

		/** 密码 */
		public static final String PASS_WORD = "v_login_password";
		
		/** 新密码 */
		public static final String NEW_PASS_WORD = "v_new_pass_word";
		
		/**用户ID*/
		public static final String USER_ID = "i_user_id";
		
		/**运营商*/
		public static final String V_ISP_NAME= "v_isp_name";
		
		/**用户所在地*/
		public static final String V_CITY_NAME = "v_city_name";

		
		/** IMEI */
		public static final String PHONE_IMEI = "v_imei";
		/** IMSI */
		public static final String PHONE_IMSI = "v_imsi";
		/** 手机型号 */
		public static final String PHONE_TYPE = "v_phone_type";
		/** 手机系统版本号 */
		public static final String PHONE_SYS_CODE = "v_syscode";
		/** 手机设备类型 */
		public static final String PHONE_DEVICE_TYPE = "v_device_type";
		/** APK版本号 */
		public static final String APK_VER_CODE = "v_verscode";
		/** APK版本名字 */
		public static final String APK_VER_NAME = "v_versname";
		
		/** 开始页 */
		public static final String PAGE_START = "v_start_page";
		
		/** 开始记录数  */
		public static final String RN_START = "start";
		
		/** 结束记录数 */
		public static final String RN_END = "end";
		
		/** 资产值列表 */
		public static final String ASSETS_VALUE_LIST = "assets_value_list";
		
		/** 变更后的值  */
		public static final String V_NEW_VALUE = "v_new_value";
		
		/** JSON请求参数 */
		public static final String COMMON_JSON = "v_common_json";
		
	}
	
}
