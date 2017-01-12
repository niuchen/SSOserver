package com.SSOserver.sys.company.dao;

 
 

import java.util.List;
import java.util.Map;

import com.SSOserver.common.bean.T_COMPANY;
import com.SSOserver.common.bean.T_MODULES;
import com.SSOserver.common.bean.T_USERS_INFO;
  

public interface ICompanyDao {
/**
 * 查询地市字典表
 * */
	public List<T_COMPANY> selectT_COMPANY(Map<String, Object> m);
	
 
 
}
