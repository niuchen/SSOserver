<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <% String msg=request.getParameter("msg") ;
    String[]msgzu=new String[]{
					      "您不是管理员无法进入!"
					    };
    
    %>
  <div align="center">  <%=msgzu[Integer.valueOf(msg)] %></div>
                <div align="center"><a href="logout.action"  style="border-bottom:1px solid white;" >重新登陆</a></div> 

    <br>
  </body>
</html>
