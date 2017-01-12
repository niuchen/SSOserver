package com.SSOserver.sys.company.service;

  

 

import java.util.List;
import java.util.Map;

import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.T_COMPANY;
import com.SSOserver.common.bean.T_USERS_INFO;
import com.SSOserver.sys.user.pojo.EaysUiTreepojo;
 

public interface ICompanyService {
 
	/**
	 * 查询地市字典表
	 * **/
	public List<T_COMPANY> selectT_COMPANY(Map<String, Object> m);
	
	 
}
