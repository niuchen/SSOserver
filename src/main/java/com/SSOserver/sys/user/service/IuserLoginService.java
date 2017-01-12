package com.SSOserver.sys.user.service;

  

 

import java.util.List;
import java.util.Map;

import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.T_USERS_INFO;
import com.SSOserver.sys.user.pojo.EaysUiTreepojo;
 

public interface IuserLoginService {
 
	/**
	 * 返回系统模块的树结果
	 * **/
	public List<EaysUiTreepojo> sysmoduletree();
	
	/**
	 * 查询分页用户列表
	 * **/
	public List<T_USERS_INFO> selectuser(Map m);
	public Integer selectuserCnt(Map m);
	public Integer selectuserseq();
	/**
	 * 登陆用户验证 查询用户信息
	 * */
	public T_USERS_INFO getuserlogin(Map m);
	/**
	 * 有条件的查询用户信息
	 * */
	public List<T_USERS_INFO> selectuserwhere(Map m);
	/**
	 * 添加账户
	 * **/
	public OperateState insertuser(T_USERS_INFO user);
	/**更新用户**/
	 public OperateState updateusers(T_USERS_INFO user);
	 /**删除用户**/
	 public OperateState deleteusers(Map m);
	 /**启用禁用*/
	 public OperateState Enable_disableuser(Map m);
	 /**修改密码**/
	 public OperateState updatepaw(String i_user_id,String uppaw);
	 /**查询账号当前密码是否正确 NULL错误 正确返回用户信息**/
	 public T_USERS_INFO selectdatepaw(String v_login_name,String paw);
	 
}
