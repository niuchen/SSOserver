package com.SSOserver.sys.user.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.SSOserver.common.HttpUtil;
import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.T_USERS_INFO;
import com.baomidou.kisso.common.encrypt.MD5;
import com.baomidou.kisso.exception.KissoException;
import com.baomidou.kisso.web.WebKissoConfigurer;
import com.google.gson.Gson;

/**
 * 用户账号与子系统同步 niuchen 2016-9-8
 * **/
@Repository("UserSubsystem")
public class UserSubsystem {

	private static String towerurl = "";
	// 铁塔tower项目添加删除用户接口路径
	private static String towerinseruser = "/users/HTTPinterface_Openanaccount.action";
	// 回滚同步删除
	private static String towerdeleteuser = "/users/HTTPinterface_httpdeleteuser_id.action";
	// 铁塔tower项目修改用户接口路径
	private static String towerupdateuser = "/users/HTTPinterface_updateuser.action";

	private static String towerprourl = "";
	// 铁塔towerpro项目添加用户删除接口路径
	private static String towerproinseruser = "/users/HTTPinterface_Openanaccount.action";
	private static String towerprodeleteuser = "/users/HTTPinterface_httpdeleteuser_id.action";
	// 铁塔tower项目修改用户接口路径
	private static String towerproupdateuser = "/users/HTTPinterface_updateuser.action";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String ssoPropPath = "/properties/sso.properties";
	private static boolean isf = false;
	private String key = null;

	public UserSubsystem() {
		isf = init();
	}

	public boolean init() {
		try {
			Properties prop = new Properties();// 属性集合对象
			// System.out.println(UserSubsystem.class.getResource(ssoPropPath));
			InputStream in = UserSubsystem.class
					.getResourceAsStream(ssoPropPath);
			prop.load(in);
			towerurl = (String) prop.get("Subsystem.tower.url");
			towerprourl = (String) prop.get("Subsystem.towerpro.url");
			key = (String) prop.get("sso.secretkey");
			if ("".equals(towerurl) || "".equals(towerprourl) || "".equals(key)) {
				return false;
			}
			// System.out.println(towerurl+" : "+towerprourl);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("初始化HTTP接口调用配置文件异常:"
					+ UserSubsystem.class.getResource(ssoPropPath)
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/** 向郑州铁塔资产巡检 tower项目 子系统同步修改用户信息 **/
	public OperateState tower_user_update(T_USERS_INFO tuser) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerurl + towerupdateuser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", tuser
						.getI_user_id().toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", tuser
						.getV_login_name()));// 登陆名
				params.add(new BasicNameValuePair("v_real_name", tuser
						.getV_real_name())); // 真是姓名
				params.add(new BasicNameValuePair("v_tel_num", tuser
						.getV_user_tel()));// 用户电话
				params.add(new BasicNameValuePair("i_subcomp_id", tuser
						.getI_subcomp_id().toString()));// 地市
				params.add(new BasicNameValuePair("i_admin_tag", tuser
						.getIs_area_manager().toString()));// 管理员表示 1：是 0：否
				params.add(new BasicNameValuePair("v_subcomp_name", tuser
						.getV_subcomp_name()));// 地市名称
				StringBuffer md5token = new StringBuffer();
				md5token.append(tuser.getI_user_id().toString());
				md5token.append(",");
				md5token.append(tuser.getV_login_name());
				md5token.append(",");
				md5token.append(tuser.getV_real_name());
				md5token.append(",");
				md5token.append(tuser.getV_user_tel());
				md5token.append(",");
				md5token.append(tuser.getI_subcomp_id().toString());
				md5token.append(",");
				md5token.append(tuser.getIs_area_manager().toString());
				md5token.append(",");
				md5token.append(tuser.getV_subcomp_name().toString());
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("tower通过返回状态码1:" + status);
					System.out.println("tower通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}

			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加账户-HTTP子系统接口调用异常:" + towerurl + e.getMessage());
			return new OperateState(3, null, "添加账户-HTTP子系统接口调用异常:"
					+ e.getMessage(), e.getMessage());
		}
	}

	/** 向郑州铁塔资产巡检 tower项目 子系统同步用户信息 **/
	public OperateState tower_user_insert(T_USERS_INFO tuser) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerurl + towerinseruser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", tuser
						.getI_user_id().toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", tuser
						.getV_login_name()));// 登陆名
				params.add(new BasicNameValuePair("v_real_name", tuser
						.getV_real_name())); // 真是姓名
				params.add(new BasicNameValuePair("v_tel_num", tuser
						.getV_user_tel()));// 用户电话
				params.add(new BasicNameValuePair("i_subcomp_id", tuser
						.getI_subcomp_id().toString()));// 地市
				params.add(new BasicNameValuePair("i_admin_tag", tuser
						.getIs_area_manager().toString()));// 管理员表示 1：是 0：否
				params.add(new BasicNameValuePair("v_subcomp_name", tuser
						.getV_subcomp_name()));// 地市名称
				StringBuffer md5token = new StringBuffer();
				md5token.append(tuser.getI_user_id().toString());
				md5token.append(",");
				md5token.append(tuser.getV_login_name());
				md5token.append(",");
				md5token.append(tuser.getV_real_name());
				md5token.append(",");
				md5token.append(tuser.getV_user_tel());
				md5token.append(",");
				md5token.append(tuser.getI_subcomp_id().toString());
				md5token.append(",");
				md5token.append(tuser.getIs_area_manager().toString());
				md5token.append(",");
				md5token.append(tuser.getV_subcomp_name().toString());
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("tower通过返回状态码1:" + status);
					System.out.println("tower通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}

			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加账户-HTTP子系统接口调用异常:" + towerurl + e.getMessage());
			return new OperateState(3, null, "添加账户-HTTP子系统接口调用异常:"
					+ e.getMessage(), e.getMessage());
		}
	}

	/** 向郑州铁塔资产巡检 tower项目 子系统删除指定ID的用户 用于回滚 登录名暂时只传递 **/
	public OperateState tower_delete_user(Long userid, String login_name) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerurl + towerdeleteuser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", userid
						.toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", login_name));// 登陆名

				StringBuffer md5token = new StringBuffer();
				md5token.append(userid);
				md5token.append(",");
				md5token.append(login_name);
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("tower通过返回状态码1:" + status);
					System.out.println("tower通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}
			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除账户-HTTP子系统接口调用异常:" + towerurl + e.getMessage());
			return new OperateState(3, null, "删除户-HTTP子系统接口调用异常:"
					+ e.getMessage(), e.getMessage());
		}
	}

	/** 向郑州铁塔资产巡检 towerpor项目 子系统同步用户信息 **/
	public OperateState towerpor_user_insert(T_USERS_INFO tuser) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerprourl + towerproinseruser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", tuser
						.getI_user_id().toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", tuser
						.getV_login_name()));// 登陆名
				params.add(new BasicNameValuePair("v_real_name", tuser
						.getV_real_name())); // 真是姓名
				params.add(new BasicNameValuePair("v_tel_num", tuser
						.getV_user_tel()));// 用户电话
				params.add(new BasicNameValuePair("i_subcomp_id", tuser
						.getI_subcomp_id().toString()));// 地市
				params.add(new BasicNameValuePair("i_admin_tag", tuser
						.getIs_area_manager().toString()));// 管理员表示 1：是 0：否
				params.add(new BasicNameValuePair("v_subcomp_name", tuser
						.getV_subcomp_name()));// 地市名称
				StringBuffer md5token = new StringBuffer();
				md5token.append(tuser.getI_user_id().toString());
				md5token.append(",");
				md5token.append(tuser.getV_login_name());
				md5token.append(",");
				md5token.append(tuser.getV_real_name());
				md5token.append(",");
				md5token.append(tuser.getV_user_tel());
				md5token.append(",");
				md5token.append(tuser.getI_subcomp_id().toString());
				md5token.append(",");
				md5token.append(tuser.getIs_area_manager().toString());
				md5token.append(",");
				md5token.append(tuser.getV_subcomp_name().toString());
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("towerporuser通过返回状态码1:" + status);
					System.out.println("towerporuser通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}

			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加账户-HTTP子系统接口调用异常:" + towerprourl);
			return new OperateState(3, null, "添加账户-HTTP子系统接口调用异常:"
					+ towerprourl, e.getMessage());
		}
	}

	/** 向 towerpor项目 子系统修改 同步用户信息 **/
	public OperateState towerpor_user_uodate(T_USERS_INFO tuser) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerprourl + towerproupdateuser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", tuser
						.getI_user_id().toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", tuser
						.getV_login_name()));// 登陆名
				params.add(new BasicNameValuePair("v_real_name", tuser
						.getV_real_name())); // 真是姓名
				params.add(new BasicNameValuePair("v_tel_num", tuser
						.getV_user_tel()));// 用户电话
				params.add(new BasicNameValuePair("i_subcomp_id", tuser
						.getI_subcomp_id().toString()));// 地市
				params.add(new BasicNameValuePair("i_admin_tag", tuser
						.getIs_area_manager().toString()));// 管理员表示 1：是 0：否
				params.add(new BasicNameValuePair("v_subcomp_name", tuser
						.getV_subcomp_name()));// 地市名称
				StringBuffer md5token = new StringBuffer();
				md5token.append(tuser.getI_user_id().toString());
				md5token.append(",");
				md5token.append(tuser.getV_login_name());
				md5token.append(",");
				md5token.append(tuser.getV_real_name());
				md5token.append(",");
				md5token.append(tuser.getV_user_tel());
				md5token.append(",");
				md5token.append(tuser.getI_subcomp_id().toString());
				md5token.append(",");
				md5token.append(tuser.getIs_area_manager().toString());
				md5token.append(",");
				md5token.append(tuser.getV_subcomp_name().toString());
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("towerporuser通过返回状态码1:" + status);
					System.out.println("towerporuser通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}

			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加账户-HTTP子系统接口调用异常:" + towerprourl);
			return new OperateState(3, null, "添加账户-HTTP子系统接口调用异常:"
					+ towerprourl, e.getMessage());
		}
	}

	/** 向郑州铁塔资产巡检 towerpro项目 子系统删除指定ID的用户 用于回滚 登录名暂时只传递 **/
	public OperateState towerpro_delete_user(Long userid, String login_name) {
		try {
			if (isf) {
				HttpUtil httpu = new HttpUtil(towerprourl + towerprodeleteuser);
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加参数
				params.add(new BasicNameValuePair("i_user_id", userid
						.toString())); // 用户ID
				params.add(new BasicNameValuePair("v_login_name", login_name));// 登陆名

				StringBuffer md5token = new StringBuffer();
				md5token.append(userid);
				md5token.append(",");
				md5token.append(login_name);
				String md5tokenjson = MD5
						.getSignature(md5token.toString(), key);
				// System.out.println("加密前" + md5token);
				// System.out.println("加密后" + md5tokenjson);
				params.add(new BasicNameValuePair("md5tokenjson", md5tokenjson));
				OperateState result = httpu.excute(params);
				if (result.getResult() == 1) {
					String status = httpu.getStatus();
					String statuscode = httpu.getStatusCode();
					System.out.println("tower通过返回状态码1:" + status);
					System.out.println("tower通过返回状态码2:" + statuscode);
					return new OperateState(1, null, "同步成功!", null);
				} else {
					return result;
				}
			} else {
				return new OperateState(2, null, "配置文件获取失败!请联系管理员!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除账户-HTTP子系统接口调用异常:" + towerurl + e.getMessage());
			return new OperateState(3, null, "删除户-HTTP子系统接口调用异常:"
					+ e.getMessage(), e.getMessage());
		}
	}

	public static String getTowerurl() {
		return towerurl;
	}

	public static String getTowerprourl() {
		return towerprourl;
	}

	public String getSsoPropPath() {
		return ssoPropPath;
	}

	public static boolean isIsf() {
		return isf;
	}

	public static void main(String s[]) {
		UserSubsystem u = new UserSubsystem();
		boolean isf = u.init();
	}
}
