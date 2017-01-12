package com.SSOserver.sys.company.controller;

 
 

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SSOserver.common.BaseAction;
import com.SSOserver.common.bean.T_COMPANY;
import com.SSOserver.sys.company.service.ICompanyService;

  
 
@Controller
@RequestMapping("/CompanyController")
public class CompanyController extends BaseAction{
	@Resource(name="CompanyServiceImp")
	private ICompanyService CompanyServiceImp;
 
 	/**
	 * 功能: 查询地市数据
	 * 返回: 
 	 * **/
 	@ResponseBody
	@RequestMapping("/selectcompany")
 	List<T_COMPANY>   selectT_COMPANY(String username,String userpwd,HttpServletRequest request,HttpServletResponse response,Model model){
 		Map<String,Object> map=new HashMap<String,Object>();
 		List<T_COMPANY> list=CompanyServiceImp.selectT_COMPANY(map);
  		map.put("list",list);
		return list;
 	}
	 
	
   }
