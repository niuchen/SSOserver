/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.springmvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;

/**
 * 登录
 */
//@Controller
public class LoginController extends BaseController {

	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		SSOToken st = SSOHelper.getToken(request);
		if (st != null) {
 			System.out.println("登陆成功");
			System.out.println("返回"+HttpUtil.decodeURL(request.getParameter("ReturnURL")));
 			return "index";
		}
		System.out.println(HttpUtil.decodeURL(request.getParameter("ReturnURL"))+"  :没有登陆token  "+request.getRequestURI());
		String Url=request.getParameter("ReturnURL");
		if("".equals(Url)||Url==null){
			Url=null;
			 request.setAttribute("URL", "");
		}else{
		 request.setAttribute("URL", HttpUtil.decodeURL(Url));
		}
		return "login";
	}

	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/loginpost")
	public String loginpost(HttpServletRequest request,HttpServletResponse response) {
		/**
		 * 生产环境需要过滤sql注入
		 */
		WafRequestWrapper req = new WafRequestWrapper(request);
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String URL = req.getParameter("URL");
		
		if (("kisso".equals(username) && "123".equals(password))||1==1) {
			
			/*
			 * authSSOCookie 设置 cookie 同时改变 jsessionId
			 */
			SSOToken st = new SSOToken(request);
			st.setId(15126L);
			st.setUid("yanglei");
			st.setType(1);
			System.out.println(st.jsonToken());
			//st.setObject(object) 
			//记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
 		//String rememberMe = req.getParameter("rememberMe");
 	//	System.out.println("rememberMe:"+rememberMe);
//			if ( "on".equals(rememberMe) ) {
        	request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 86400); 
//			}
			SSOHelper.setSSOCookie(request, response, st, true);
			System.out.println(URL);
			//if("".equals(URL)||URL==null||"http://127.0.0.1:8080/kisso/".equals(URL)){
				//return redirectTo("http://127.0.0.1:8080/kisso/index");
			//}
			//return redirectTo(URL);
			//System.out.println(req.getAttribute("ReturnURL"));
			/*
			 * 登录需要跳转登录前页面，自己处理 ReturnURL 使用 
			 * HttpUtil.decodeURL(xx) 解码后重定向
			 */
		//	System.out.println(HttpUtil.decodeURL(req.getParameter("ReturnURL")));
		//	return	redirectTo(HttpUtil.decodeURL(URL));
			// return redirectTo("http://127.0.0.1:8088/kissocilent/static/test.html?a="+Math.random());
		 //	 return "forward:index.action";
			
			
			
			 return redirectTo("index.action");
		}
		return "login";
	}
}
