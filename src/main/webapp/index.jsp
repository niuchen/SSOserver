<%@page import="com.SzMIS.sys.user.pojo.UserLogVo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>统一登陆门户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
   <jsp:include page="/inc1.4.5.jsp"></jsp:include>
   
		<script type="text/javascript" charset="utf-8"> 
 	$(document).ready(function(){
		//debugger;
		//alert(${sessionScope.user.v_login_name});
		if('${sessionScope.user}' == null||'${sessionScope.user}'=="")
		{
			$.messager.show({title:'提示',msg:'您还未登录!请登录后操作...'});
			   setTimeout(function(){
			     window.location.href = 'login.action';
			   }, 1000);
		}
		else
		{
			$.messager.show({
				title : '提示',
				width:230,
				height:150,
				msg : function(){
				    var msg ='${sessionScope.user.v_real_name}'+' 您好!<br/>';
				     if('${sessionScope.user.maxlogdate}' != null && '${sessionScope.user.maxlogdate}' != ''){
				       msg += '上次登陆时间: '+ '${sessionScope.user.maxlogdate}' + '<br/>';
				     }else{
				    	 msg += '欢迎你首次登陆系统!~'+'<br/>';
				     }
				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != ''){
				        msg += '上次登陆IP: ' + '${sessionScope.user.maxlogip}' + '<br/>';
				     }
				     msg += '本次登陆IP：' + '${sessionScope.user.ip}' + '<br/>';
				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != '' && '${sessionScope.user.maxlogip}' != '${sessionScope.user.ip}'){
				    	 msg += '<font color="red">两次登陆IP不一致!</font>';
				     }
				     return msg;
				  }
				});
		}
		}
	); 
	function iframeshow(type){
	  if(type==1){
	  window.document.getElementById("iframe1").src="http://127.0.0.1:8080/tpro"+"/users/loginTagOSS.action?_dc="+((new Date()).getTime());
	  }
	   if(type==2){
	    window.document.getElementById("iframe1").src="http://127.0.0.1:8080/tower"+"/users/loginTagOSS.action?_dc="+((new Date()).getTime());
	  }
	  
	   if(type==3){
	 //  window.document.getElementById("iframe1").src="http://127.0.0.1:8088/OSSserver/main.jsp?_dc="+((new Date()).getTime());
 	     window.open("main.jsp");
	  }
	}
	</script>
   </head>
   <body>
 
	 空白首页.
	
	
</body>
</html>
