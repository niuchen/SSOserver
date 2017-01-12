package com.SSOserver.sys.user.controller;

 
 

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.SSOserver.common.API.KEY;
import com.SSOserver.common.BaseAction;
import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.ResponseData;
import com.SSOserver.common.bean.ResponseEnum;
import com.SSOserver.common.bean.T_USERS_INFO;
import com.SSOserver.common.utlt.Des;
import com.SSOserver.common.utlt.StringUtil;
import com.SSOserver.sys.user.pojo.EaysUiTreepojo;
import com.SSOserver.sys.user.pojo.UserLogVo;
import com.SSOserver.sys.user.service.IuserLoginService;
import com.SSOserver.sys.user.service.imp.UserSubsystem;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.encrypt.MD5;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.springmvc.controller.CaptchaUtil;
 
   
 
@Controller
//@RequestMapping("/userlogincontroller")
public class UserLoginController extends BaseAction{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name="UserLogServiceImp")
	private IuserLoginService userlogserviceimp;
	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		SSOToken st = SSOHelper.getToken(request);
	    Object ouser=request.getSession().getAttribute("user");
	   //如果已经登陆
		if (st != null&&ouser!=null) {
 			System.out.println("在线登陆人数:"+SSOHelper.getLoginCount(request));
 		//	System.out.println("登陆成功");
		//	System.out.println("返回"+HttpUtil.decodeURL(request.getParameter("ReturnURL")));
  			return "index";
		} 
//		if(ouser==null){
//			System.out.println("没有存在session");
//		}else{
//			System.out.println(HttpUtil.decodeURL(request.getParameter("ReturnURL"))+"  :没有登陆token  "+request.getRequestURI());
// 		}
		String Url=request.getParameter("ReturnURL");
		if("".equals(Url)||Url==null){
			Url=null;
			 request.setAttribute("URL", "");
		}else{
		 request.setAttribute("URL", HttpUtil.decodeURL(Url));
		}
		System.out.println("SSOToken=null");
		return "login";
	}
 	/**
	 * 功能: 登陆
	 * 返回: 
	 *  http://127.0.0.1:8088/szwq_sys/enterprisescontroller/getALLEnterprises.action	
	 * **/
 	@ResponseBody
	@Login(action = Action.Skip)
	@RequestMapping("/Userlogin")
	Map Userlog(String username,String userpwd,HttpServletRequest request,HttpServletResponse response,Model model){
 		Map<String,Object> map=new HashMap<String,Object>();
 		try {
 			 
 	   //   request.getParameter("susername");
 	   //   request.getParameter("suserpwd");
   //System.out.println(username+" "+userpwd+"---"+   request.getParameter("password"));
 	
// 		String vcode= request.getParameter("vcode");
//  		Object verify=  request.getSession().getAttribute("verify");
//  		if(verify==null||vcode==null){
// 			map.put("msg", "请输入验证码");
//				return map;
// 		}else if(!verify.toString().toUpperCase().equals(vcode.toUpperCase())){
// 			map.put("msg", "验证码错误");
//				return map;
// 		}
 			Map m=new HashMap();
 		if(username==null||userpwd==null||"".equals(username)||"".equals(userpwd)){
 			m.put("msg","账号或密码为空,请重新登陆!");
 			return m;
 		}
 	 	m.put("v_login_password",MD5.toMD5(userpwd));
 	 	m.put("v_login_name", username);
 		m.put("i_delete_state",0);
 		T_USERS_INFO user= userlogserviceimp.getuserlogin(m);
  		 if (user!=null) {
 			UserLogVo tm=new UserLogVo();
 			tm.setV_login_name(username);
 			tm.setV_login_password(userpwd);
 			tm.setV_real_name(user.getV_real_name());
 			tm.setI_user_id(user.getI_user_id());
 		//	tm.set("2014-11-11");
 			tm.setMaxlogdate(user.getV_login_imei());
 	//		tm.setMaxlogip(this.getIpAddr(request));
 			tm.setIp(this.getIpAddr(request));
 			tm.setI_admin_tag(user.getI_admin_tag());
 			tm.setV_subcomp_name(user.getV_subcomp_name());
 			tm.setI_subcomp_id(user.getI_subcomp_id());
 			
 			//cookie中存储数据
 			SSOToken st = new SSOToken(request);
			st.setId(user.getI_user_id());//用户ID
			st.setUid(user.getI_subcomp_id()+"@"+user.getV_subcomp_name());//用户地市ID和名称
			//st.setApp("测试系统名称");
			 //st.setObject(user);
			st.setType(user.getIs_area_manager());//存储是否地区管理员
         	//request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 86400); //cookie过期时间 24小时
 			SSOHelper.setSSOCookie(request, response, st, true);
 			
 			request.getSession().setAttribute("user",tm);
 			
 			String ssoPropPath = "/properties/sso.properties";
 			Properties prop = new Properties();// 属性集合对象
			//System.out.println(UserSubsystem.class.getResource(ssoPropPath));
			InputStream in = UserSubsystem.class
					.getResourceAsStream(ssoPropPath);
			prop.load(in);
			String towerurl = (String) prop.get("Subsystem.tower.url");
			String towerprourl = (String) prop.get("Subsystem.towerpro.url");
		 
				if ("".equals(towerurl) || "".equals(towerprourl) ) {
					map.put("msg", "系统配置URL文件为空!请联系管理员!");
	 				return map;
				}else{
					Map urlmap=new HashMap();
					urlmap.put("towerurl1",towerurl);
					urlmap.put("towerprourl",towerprourl);
					request.getSession().setAttribute("urlmap",urlmap);
				}
 			}else{
 				map.put("msg", "账号密码错误");
 				return map;
 			}
 			//request.getSession().putValue("users", tm);
		} catch (Exception e) {
		 e.printStackTrace();
		}
 		 map.put("success", true);
 		
		return map;
 	}
	 
	@RequestMapping("/main")
	public String main(HttpServletRequest request,HttpServletResponse response) {
		return "main.jsp";
	}
	
	 /**
	  * 返回登陆后布局页面的JSP页面
	  * **/
	@RequestMapping("/Layout")
	ModelAndView Layout(String type,HttpServletRequest request,HttpServletResponse response,Model model){
 		Map<String,Object> map=new HashMap<String,Object>();
 		if("layout_north".equals(type)){
   			 return  new ModelAndView("page/layout/north");
 		}else if("layout_menu".equals(type)){
    			 return  new ModelAndView("page/layout/menu");
 		}else if("layout_center".equals(type)){
   			 return  new ModelAndView("page/layout/center");
 		}else if("layout_south".equals(type)){
   			 return  new ModelAndView("page/layout/south");
 		}else{
 			return null;
 			//return new ModelAndView("example");
 		}
 	}
	
	
	
	/**
	 * 查询模块
	 * */
		@ResponseBody
		@RequestMapping("/sysmoduletree")
		List<EaysUiTreepojo>  sysmoduletree(String susername,String suserpwd,HttpServletRequest request,HttpServletResponse response,Model model){
	  		try {
	 			List<EaysUiTreepojo>  list=	userlogserviceimp.sysmoduletree();
	 			return list;
	 		}catch(Exception e){
	 			e.printStackTrace();
	 		}
	 		return null;
		}
		
		
		/**********************
		 * 用户查询
		 */
		@RequestMapping("/selectuser")
		@ResponseBody
		public Map selectuser(HttpServletRequest req) throws Exception{
 			Map map = new HashMap();
			int start = Integer.valueOf(req.getParameter("page"));
			int limit = Integer.valueOf(req.getParameter("rows"));
			String v_role_name = req.getParameter("selectRoleSels");
  			int  pageNumber = this.userlogserviceimp.selectuserCnt(map);
			start = (start -1)*limit;
			int end  = start+limit >= pageNumber ? pageNumber : start + limit;
  			map.put("start",start);
			map.put("limit", end);
			List<T_USERS_INFO> list = this.userlogserviceimp.selectuser(map);
			map.clear();
			map.put("total", pageNumber);
			map.put("rows", list);
 			return map;
		}
		
		/**
		 * 查询用户列表
		 * **/
		@RequestMapping("/selectuserlist")
 		public String selectuserlist(HttpServletRequest req) throws Exception{
 		 return "sys/users/userslist";
		}
		
		
		/**
		 * 验证码 （注解跳过权限验证）
		 */
		@Login(action = Action.Skip)
		@ResponseBody
		@RequestMapping("/verify")
		public void verify(HttpServletRequest request,HttpServletResponse response) {
			try {
				String verifyCode = CaptchaUtil.outputImage(response.getOutputStream());
				//System.out.println("验证码:" + verifyCode);
				request.getSession().setAttribute("verify", verifyCode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 退出登录
		 */
		@RequestMapping("/logout")
		public String logout(HttpServletRequest request,HttpServletResponse response) {
			/**
			 * <p>
			 * SSO 退出，清空退出状态即可
			 * </p>
			 * 
			 * <p>
			 * 子系统退出 SSOHelper.logout(request, response); 注意 sso.properties 包含 退出到
			 * SSO 的地址 ， 属性 sso.logout.url 的配置
			 * </p>
			 */
			SSOHelper.clearLogin(request, response);
			return redirectTo("/login.action");
			//return "index";
		}
		/**
		 * 添加账户
		 * */
		@RequestMapping("/insertuser")
		@ResponseBody
		public Map insertuser(HttpServletRequest request,HttpServletResponse response) {
			Map m=new HashMap();
			 
 			String v_login_name=request.getParameter("v_login_name");
			String v_login_password=request.getParameter("v_login_password");
			String v_real_name=request.getParameter("v_real_name");
			String v_user_tel=request.getParameter("v_user_tel");
			String v_company_name=request.getParameter("v_company_name");//地市ID
			String v_company_name2=request.getParameter("v_company_name2");//地市名称
			String i_admin_tag=request.getParameter("i_admin_tag");//是否超级管理员
 			String is_area_manager=request.getParameter("is_area_manager");//是否地市管理员	
			if(v_login_name==null||"".equals(v_login_name)){
				m.put("op", new OperateState(2, null,"登陆名为空",null));
				return m;
			}else if(v_company_name==null||"".equals(v_company_name)||v_company_name2==null||"".equals(v_company_name2)){
				m.put("op", new OperateState(2, null,"地市为空",null));
				return m;
			}else if(v_login_password==null||"".equals(v_login_password)){
				m.put("op", new OperateState(2, null,"密码为空",null));
				return m;
			}else if(v_real_name==null||"".equals(v_real_name)){
				m.put("op", new OperateState(2, null,"账户人姓名为空",null));
				return m;
			}else if(v_user_tel==null||"".equals(v_user_tel)){
				m.put("op", new OperateState(2, null,"账户人手机号为空",null));
				return m;
			}else if(i_admin_tag==null||"".equals(i_admin_tag)){
				m.put("op", new OperateState(2, null,"是否超级管理员为空",null));
				return m;
			}else if(is_area_manager==null||"".equals(is_area_manager)){
				m.put("op", new OperateState(2, null,"是否地市管理员为空",null));
				return m;
			}
			m.put("v_login_name", v_login_name);
//			m.put("v_login_password", v_login_password);
//			m.put("v_real_name", v_real_name);
			List list=userlogserviceimp.selectuserwhere(m);
			if(list!=null){
				if(list.size()>0){
					m.put("op", new OperateState(2, null,"账户登录名已存在",null));
					return m;
				}
			}
			 Integer useq=userlogserviceimp.selectuserseq();
			T_USERS_INFO user=new T_USERS_INFO();
			user.setI_user_id(Long.valueOf(useq));
			user.setV_login_name(v_login_name);
			user.setV_login_password(MD5.toMD5(v_login_password));
			user.setV_real_name(v_real_name);
			user.setV_user_tel(v_user_tel);
			user.setI_user_state((long)0);
			user.setI_delete_state(0);
			user.setV_company_name(v_company_name2);
			user.setI_subcomp_id(Long.valueOf(v_company_name));
			user.setV_subcomp_name(v_company_name2);
			user.setI_admin_tag(Long.valueOf(i_admin_tag));
			user.setIs_area_manager(Integer.valueOf(is_area_manager));
 	    	OperateState op=	userlogserviceimp.insertuser(user);
		   m.clear();
		   m.put("op", op);
			return m;
		}
		
		/**
		 * 启用禁用
		 * */
		@RequestMapping("/Enable_disableuser")
		@ResponseBody
		public Map Enable_disableuser(HttpServletRequest request,HttpServletResponse response) {
			Map m=new HashMap();
			String i_delete_state=request.getParameter("i_delete_state");
			String i_user_id=request.getParameter("i_user_id");
		 
			if(i_delete_state==null||"".equals(i_delete_state)){
				m.put("op", new OperateState(2, null,"启用禁用标识为空",null));
				return m;
			}
			if(i_user_id==null||"".equals(i_user_id)){
				m.put("op", new OperateState(2, null,"账号ID标识为空",null));
				return m;
			}
		//	System.out.println(i_delete_state);
		//	System.out.println(i_user_id);
			m.put("i_delete_state", i_delete_state);
			m.put("i_user_id", i_user_id);
//			m.put("v_login_password", v_login_password);
//			m.put("v_real_name", v_real_name);
			OperateState op =userlogserviceimp.Enable_disableuser(m);
			 m.clear();
		     m.put("op", op);
		     return m;
		}
		/**
		 * 修改账户
		 * */
		@RequestMapping("/updateuser")
		@ResponseBody
		public Map updateuser(HttpServletRequest request,HttpServletResponse response) {
			Map m=new HashMap();
			String v_login_name=request.getParameter("upv_login_name");
 			String upi_user_id=request.getParameter("upi_user_id");
			String v_login_password=request.getParameter("upv_login_password");
			String v_real_name=request.getParameter("upv_real_name");
			String v_user_tel=request.getParameter("upv_user_tel");
			String v_company_name=request.getParameter("upv_company_name");//地市ID
 			String upv_company_name=request.getParameter("v_company_name2");//地市名称
 			String i_admin_tag=request.getParameter("upi_admin_tag");//是否超级管理员
 			String is_area_manager=request.getParameter("upis_area_manager");//是否地市管理员		
 			
			if(v_login_name==null||"".equals(v_login_name)){
				m.put("op", new OperateState(2, null,"登陆名为空",null));
				return m;
			}
//			else if(v_login_password==null||"".equals(v_login_password)){
//				m.put("op", new OperateState(2, null,"密码为空",null));
//				return m;
//			}
			else if(v_company_name==null||"".equals(v_company_name)||upv_company_name==null||"".equals(upv_company_name)){
				m.put("op", new OperateState(2, null,"地市为空",null));
				return m;
			}else if(v_real_name==null||"".equals(v_real_name)){
				m.put("op", new OperateState(2, null,"账户人姓名为空",null));
				return m;
			}else if(v_user_tel==null||"".equals(v_user_tel)){
				m.put("op", new OperateState(2, null,"账户人手机号为空",null));
				return m;
			}else if(upi_user_id==null||"".equals(upi_user_id)){
				m.put("op", new OperateState(2, null,"用户编号为空",null));
				return m;
			}else if(i_admin_tag==null||"".equals(i_admin_tag)){
				m.put("op", new OperateState(2, null,"是否超级管理员为空",null));
				return m;
			}else if(is_area_manager==null||"".equals(is_area_manager)){
				m.put("op", new OperateState(2, null,"是否地市管理员为空",null));
				return m;
			}
			
			m.put("v_login_name", v_login_name);
//			m.put("v_login_password", v_login_password);
//			m.put("v_real_name", v_real_name);
			List<T_USERS_INFO> list=userlogserviceimp.selectuserwhere(m);
			//判断登录名是否重复
			if(list!=null){
				if(list.size()>1){
					m.put("op", new OperateState(2, null,"存在多个重复登陆名数据,请联系管理员!",null));
					return m;
				}else if(list.size()==1){
 					T_USERS_INFO tuser=list.get(0);
 					//如果查询登录名,是和当前更新的数据ID一样,表示正确,是同一个数据没有改变登录名更新操作.
 					//如果查询登录名  和当前更新的数据ID不一样  表示这个登录名已经存在
 					if(!tuser.getI_user_id().equals(Long.valueOf(upi_user_id))){
	 					m.put("op", new OperateState(2, null,"要更新的账户登录名已存在!请换一个登录名!",null));
						return m;
 					}
				} 
			}
			
			T_USERS_INFO user=new T_USERS_INFO();
			user.setI_user_id(Long.valueOf(upi_user_id));
			user.setV_login_name(v_login_name);
			//user.setV_login_password(MD5.toMD5(v_login_password));
			user.setV_real_name(v_real_name);
			user.setV_user_tel(v_user_tel);
 			user.setV_company_name(upv_company_name);
			user.setI_subcomp_id(Long.valueOf(v_company_name));
			user.setV_subcomp_name(upv_company_name);
			user.setI_admin_tag(Long.valueOf(i_admin_tag));
			user.setIs_area_manager(Integer.valueOf(is_area_manager));
 			//user.setI_user_state((long)0);
	    	OperateState op=	userlogserviceimp.updateusers(user);
	    	op.setReuslt_value(user);
		   m.clear();
		   m.put("op", op);
			return m;
		}
		
		/***删除用户*/
		@RequestMapping("/deleteusers")
		@ResponseBody
		public Map deleteusers(HttpServletRequest request,HttpServletResponse response) {
			Map m=new HashMap();
 			String upi_user_id=request.getParameter("i_user_id");
 			m.put("i_user_id", upi_user_id);
 			OperateState op=	userlogserviceimp.deleteusers(m);
 		    m.clear();
		    m.put("op", op);
			return m;
		}
		
		/**
		 * 修改密码
		 * */
		@Login(action = Action.Skip)
		@RequestMapping("/mdfPsd")
		@ResponseBody
		public  ResponseData<String> mdfPsd(HttpServletRequest request,HttpServletResponse response) {
			ResponseData<String>   data = new ResponseData<String>();
			try {
				setAllParam();
				if(StringUtil.isEmpty(getParam(KEY.USER_ID)) || StringUtil.isEmpty(getParam(KEY.USER_NAME))
						|| StringUtil.isEmpty(getParam(KEY.PASS_WORD)) || StringUtil.isEmpty(getParam(KEY.NEW_PASS_WORD))){
					data.setMessage("请求参数为NULL!");
					data.setResultType(ResponseEnum.ERROR);
					return data;
					}
				setParam(KEY.USER_NAME, Des.decryptDES(getParam(KEY.USER_NAME), KEY.DES_CODE));
				setParam(KEY.PASS_WORD, Des.decryptDES(getParam(KEY.PASS_WORD), KEY.DES_CODE));
				setParam(KEY.NEW_PASS_WORD,Des.decryptDES(getParam(KEY.NEW_PASS_WORD), KEY.DES_CODE));
				setParam(KEY.PASS_WORD,MD5.toMD5(getParam(KEY.PASS_WORD)));
				
				//setParam(KEY.USER_ID, Des.decryptDES(getParam(KEY.USER_ID), KEY.DES_CODE));
				
				T_USERS_INFO appUser=	userlogserviceimp.selectdatepaw(getParam(KEY.USER_NAME), getParam(KEY.PASS_WORD));
				
				if(appUser == null){
					data.setMessage("输入的原始密码错误!");
					data.setResultType(ResponseEnum.ERROR);
					return data;
				}else{
					if(appUser.getI_user_id().equals(Long.valueOf(getParam(KEY.USER_ID)))){
						
						setParam(KEY.NEW_PASS_WORD,MD5.toMD5(getParam(KEY.NEW_PASS_WORD)));
						OperateState op=userlogserviceimp.updatepaw(getParam(KEY.USER_ID), getParam(KEY.NEW_PASS_WORD));
						if(op.getResult()==1){
				    		data.setMessage("密码修改成功!");
							data.setResultType(ResponseEnum.SUCCESS);
						}else{
							data.setMessage("修改错误!"+op.getReuslt_hint());
							data.setResultType(ResponseEnum.ERROR);
						}
						return data;
					}else{
						data.setMessage("输入的参数错误!");
						data.setResultType(ResponseEnum.ERROR);
						return data;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				data.setMessage("异常错误!"+e);
				data.setResultType(ResponseEnum.ERROR);
				return data;
			}
			
		}
			
   }
