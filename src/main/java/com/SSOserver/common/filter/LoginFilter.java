package com.SSOserver.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SSOserver.common.bean.T_USERS_INFO;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.common.util.HttpUtil;
 





/**
 * 登录拦截器
 *
 */
public class LoginFilter implements Filter {
	//http://localhost:8080/tower/login.jsp?_dc1438669146355
	//http://localhost:8080/tower/sub/ay/login.jsp
	private static String[] subcompurl = {"", "/sub/zz", "/sub/ly", "/sub/kf", "/sub/ny", "/sub/xx", "/sub/sq","/sub/zk","/sub/xy","/sub/pds","/sub/zmd","/sub/smx","/sub/jz","/sub/lh","/sub/xc","/sub/ay","/sub/hb","/sub/py","/sub/jy"}; 
	
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse  = (HttpServletResponse) response;
		String url = httpRequest.getRequestURI();
	  
		String userAgent = httpRequest.getHeader("user-agent");
 
 	
//	 
//		if(url.endsWith("loginTag.action") ||url.endsWith("aLoginState.action")
//				||url.contains("login.jsp") ||falg||url.contains("station.jsp")
//				||url.contains("tower.jsp")||url.contains("queryStation.action")
//				||url.contains("queryName.action")||url.contains("Passwordrecovery")){
//			chain.doFilter(request, response);
//			return;
//		} 
//		else{
		// // System.out.println(url);
	//	// System.out.println(url.contains("verify.action"));
		if(url.contains("logout.action")||url.contains("Userlogin")||url.contains("login")||url.contains("verify.action")
				||url.contains("msg")||url.contains("mdfPsd")){
			
			//需要根据客户端的ip地址进行安全验证限制
			//String ip = this.getIpAddr((HttpServletRequest)request);
			chain.doFilter(request, response);
			return;
		} 
		// System.out.println("没有跳过URL"+url);
		/** 非拦截URL、安全校验Cookie */
//			Token token = SSOHelper.getToken(httpRequest);
//		if (token == null) {
//			
//			// System.out.println("拦截器:tower:   没有令牌,重新登陆");
//			/**
//			 * 重新登录
//			 */
//			 SSOHelper.clearRedirectLogin(httpRequest, httpResponse);
//			return ;
//		} else {
 
			T_USERS_INFO userinfo = (T_USERS_INFO)httpRequest.getSession().getAttribute("user");		
			if(userinfo == null){

			//	String basePath ="";
		 		httpRequest.getSession().invalidate();
				// System.out.println("拦截器:tower:   没有用户信息,重新登陆");
				SSOHelper.clearRedirectLogin(httpRequest, httpResponse);
				return;
			}else{
				//// System.out.println(userinfo.getI_admin_tag());
				//判读是否管理员.如果不是管理员将不允许访问OSS项目的任何资源.  可以通过用户管理分配.
				if(!Long.valueOf(1).equals(userinfo.getI_admin_tag())){
					// System.out.println("拦截器:tower 不是管理员");
					httpResponse.sendRedirect("msg.jsp?msg=0"); 
					//chain.doFilter(httpRequest, httpResponse);
   					return;
				}
				
				// System.out.println("拦截器:tower:   验证通过统一访问");
			//	httpRequest.setAttribute(SSOConfig.SSO_TOKEN_ATTR, token);
				chain.doFilter(request, response);
			}
	//	}
		
 			
		//}
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	/**
	 * 获得客户端ip地址
	 * */
    private String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
         ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
         ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
         ip = request.getRemoteAddr();  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
         ip = request.getHeader("http_client_ip");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
         ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        // 如果是多级代理，那么取第一个ip为客户ip  
        if (ip != null && ip.indexOf(",") != -1) {  
         ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
        }  
        return ip;  
       }  
}
