package com.SSOserver.sys.company.service.imp;

 
 

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.T_COMPANY;

import com.SSOserver.sys.company.service.ICompanyService;

 

@Service("CompanyServiceImp")
public class CompanyServiceImp     implements ICompanyService{
	 

	@Resource(name = "sqlSessionFactory")
	public SqlSessionFactory sf;
	
 	@Resource(name = "transactionManager") 
 	public DataSourceTransactionManager txManager;
	
// 	   @Resource(name="DefaultIdGenerator")
// 	  public	IdGenerator idGenerator;
//	 	public void setIdGenerator(IdGenerator idGenerator) {
//	 		this.idGenerator = idGenerator;
//	 	}
	 	 @Resource(name="ICompanyDao")
	 	 com.SSOserver.sys.company.dao.ICompanyDao ICompanyDao;
	 	
	 	public List<T_COMPANY> selectT_COMPANY(Map<String, Object> m){
			return ICompanyDao.selectT_COMPANY(m);
	 	}
 
 }
