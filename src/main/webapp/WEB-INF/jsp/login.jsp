<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="login-alone">
<head>
<title>统一门户登陆</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="front/styles/bootstrap.min.css">
<link rel="stylesheet" href="front/styles/main.css">


  <jsp:include page="/inc1.4.5.jsp"></jsp:include>
      <script>
 	 
   function login(){
   
   	var userName  =  $('#username').val();
 		if(userName == ""){
			alert("用户名不能为空");
			return;
		}
		
 	var password  =  $('#userpwd').val();
		if(password == ""){
			alert("密码不能为空")
			return;
		}
   
     $('#login_loginForm').form({
			 	url : 'Userlogin.action',
			 	dataType:"json",
				success : function(result) {
  					var r = $.parseJSON(result);
  					if (r.success) {
					//	login_loginDialog.dialog('close');
						window.location.href = 'login.action';//main.html 配置失效
 					} else {
 					  alert(r.msg);
					/**	$.messager.show({
							title : '提示',
							msg : r.msg
							});**/
					}
 				}
			}).submit();
   }
       $(function(){
	    // alert("启动运行");

       });
	
function doSubmit(event)  
{  
    if (event.keyCode == 13)  
    {  
      login();
    }  
}
    </script>
 </head>
  <body>
  	<div class="login-wrap">
		<div class="login-bg"></div>
	        <div class="login-inner clearfix">
	        	<div class="login-logo"><img src="front/images/logo.png" width="220" alt="中国铁塔"></div>
	        	<div class="form-login">
		            <form action="Userlogin.action" id="login_loginForm">
	                    <div class="form-group">
	                        <input type="text" placeholder="请输入账号" class="form-control" id="username" name="username" onkeydown="doSubmit(event);">
	                    </div>
	                    <div class="form-group">
	                        <input type="password"   placeholder="请输入密码" class="form-control" id="userpwd" name="userpwd" onkeydown="doSubmit(event);">
	                    	<input id ="hideinput" type=hidden value="zz">
	                    </div>
	                    <!-- 
 	                        	<div style="margin-right: 1%;" >
                                    <input placeholder="请输入验证码" type="text" name="vcode" height="30" style="width: 30%; margin-right: 2%;">
                                    <img onclick="this.src=('verify.action?reload='+(new Date()).getTime())" 
                                     src="verify.action" width="30%" height="30" alt="">
                                </div>
 	                    -->
	                    <div class="form-group"><input type="button" value="登 录" class="btn btn-danger btn-block" onclick="login();">
	                    </div>
		            </form>
		            <div class="app-wrap clearfix">
		                <div class="app-ios">
		                    <div class="app-alter clearfix">
		                        <span class="caret"></span>
		                        <div class="code-bg"><img src="front/images/app_ios.png" width="130" height="130"></div>
		                        <a class="btn-download" href="" target="_blank"><img src="front/images/icon-download.png"></a>
		                    </div>
		                </div>
		                <div class="app-android">
		                    <div class="app-alter clearfix">
		                        <span class="caret"></span>
		                        <div class="code-bg"><img src="front/images/app_android.png" width="130" height="130"></div>
		                        <a class="btn-download" href="" target="_blank"><img src="front/images/icon-download.png"></a>
		                    </div>
		                </div>
		            </div>
	            </div>
	            <footer class="login-foot">
		            <p>Copyright &copy; 上海神洲数港科技有限公司<br>公司服务支撑热线：18337191746</p>
		        </footer>
            </div>
	    </div>
	</div>
  <!--  
      <div class="logina-logo" style="height: 55px">
        <a href="">
            <img src="static/toplogo.png" height="60" alt="">
        </a>
    </div>
    <div class="logina-main main clearfix">
        <div class="tab-con">
            <form id="login_loginForm" method="post" action="loginpost.action">
                <div id='login-error' class="error-tip"></div>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th>账户</th>
                            <td width="245">
                                <input type="text" name="username" placeholder="电子邮箱/手机号" autocomplete="off" value=""></td>
                             <input  type="hidden" name="URL"  autocomplete="off" value="${URL}">
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <th>密码</th>
                            <td width="245">
                                <input type="password" name="userpwd" placeholder="请输入密码" autocomplete="off">
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr id="tr-vcode">
                            <th>验证码</th>
                            <td width="245">
                                <div class="valid">
                                    <input type="text" name="vcode">
                                    <img onclick="this.src=('verify.action?reload='+(new Date()).getTime())" class="vcode" 
                                    src="verify.action" width="85" height="35" alt="">
                                </div>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr class="find">
                            <th></th>
                            <td>
                                <div>
                                    <label class="checkbox" for="chk11">
                                    <input style="height: auto;" id="chk11" type="checkbox" name="remember_me" >记住我</label>
                                    <a href="#">忘记密码？</a>
                                </div>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <th></th>
                            <td width="245"><input onclick="login()" class="confirm" type="button" value="登  录"></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="refer" value="site/">
            </form>
        </div>
         
    </div>
    <div id="footer">
        <div class="copyright">Copyright © 2015 kisso. All Rights Reserved.</div>
    </div>
    -->
</body>
</html>
