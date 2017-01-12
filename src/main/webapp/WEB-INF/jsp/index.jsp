<%@page import="com.SSOserver.sys.user.pojo.UserLogVo"%>
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
    <link rel="stylesheet" href="dist/css/index.css">
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
		 			 var msg="" ;
		 			  if('${sessionScope.user.v_subcomp_name}' != null && '${sessionScope.user.v_subcomp_name}' != ''){
				       msg += '所属地市: '+ '${sessionScope.user.v_subcomp_name}' + '<br/>';
				     }
				     if('${sessionScope.user.maxlogdate}' != null && '${sessionScope.user.maxlogdate}' != ''){
				       msg += '上次登陆时间: '+ '${sessionScope.user.maxlogdate}' + '<br/>';
				     }else{
				    	// msg += '欢迎你首次登陆系统!~'+'<br/>';
				     }
				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != ''){
				        msg += '上次登陆IP: ' + '${sessionScope.user.maxlogip}' + '<br/>';
				     }
				     msg += '本次登陆IP：' + '${sessionScope.user.ip}' + '<br/>';
				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != '' && '${sessionScope.user.maxlogip}' != '${sessionScope.user.ip}'){
				    	 msg += '<font color="red">两次登陆IP不一致!</font>';
				     }
					 $("#logmsg").html(msg);
		 
// 			$.messager.show({
// 				title : '提示',
// 				width:230,
// 				height:150,
// 				msg : function(){
// 				    var msg ='${sessionScope.user.v_real_name}'+' 您好!<br/>';
// 				     if('${sessionScope.user.maxlogdate}' != null && '${sessionScope.user.maxlogdate}' != ''){
// 				       msg += '上次登陆时间: '+ '${sessionScope.user.maxlogdate}' + '<br/>';
// 				     }else{
// 				    	 msg += '欢迎你首次登陆系统!~'+'<br/>';
// 				     }
// 				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != ''){
// 				        msg += '上次登陆IP: ' + '${sessionScope.user.maxlogip}' + '<br/>';
// 				     }
// 				     msg += '本次登陆IP：' + '${sessionScope.user.ip}' + '<br/>';
// 				     if('${sessionScope.user.maxlogip}' != null && '${sessionScope.user.maxlogip}' != '' && '${sessionScope.user.maxlogip}' != '${sessionScope.user.ip}'){
// 				    	 msg += '<font color="red">两次登陆IP不一致!</font>';
// 				     }
// 				     return msg;
// 				  }
// 				});
		}
		}
	); 
	function iframeshow(type){
	 
	  if(type==1){
		 window.open("${sessionScope.urlmap.towerprourl}/users/loginTagOSS.action?index=1&_dc="+((new Date()).getTime()));
	  }
	  if(type==4){
		 window.open("${sessionScope.urlmap.towerprourl}/users/loginTagOSS.action?index=2&_dc="+((new Date()).getTime()));
	  }
	   if(type==2){
	    window.open("${sessionScope.urlmap.towerurl1}/users/loginTagOSS.action?_dc="+((new Date()).getTime()));
	   // window.document.getElementById("iframe1").src="http://127.0.0.1:8088/tower1"+"/users/loginTagOSS.action?_dc="+((new Date()).getTime());
	  }
	  
	   if(type==3){
 	     window.open("main.jsp");
	  }
	}
	</script>
   </head>
  <body style="background: #eee;">
<header class="index-head-wrap">
    <div class="main-wrap">
        <div class="logo">
            <img src="dist/img/logo.png" alt="中国铁塔">
        </div>
        <div class="pull-right">
            <div class="link">
                <a href="javascript:void(0)" target="_blank">帮助中心</a>
                <a href="javascript:void(0)" target="_blank">加入收藏夹</a>
                <a href="javascript:void(0)" target="_blank">设为首页</a>
                <a href="javascript:void(0)" target="_blank">新功能介绍</a>
            </div>
            <form class="search" action="">
                <input type="text" placeholder="请输入关键字">
                <button type="submit" class="btn-submit"></button>
            </form>
        </div>
    </div>
</header>
 
<section class="main-wrap">
    <div class="left-wrap">
        <div class="mem-center">
            <div class="mem-info">
                <img src="dist/img/avatar.png" height="30">
                <span>欢迎您!：<strong> ${sessionScope.user.v_real_name} </strong></span>
               
                
            </div>
            <dl>
             <div id="logmsg"> 没有信息</div>
             <div align="center"><a href="logout.action"  style="border-bottom:1px solid white;" >退出登录</a></div> 
               <!-- <dt><a href="javascript:void(0)">待办中心：18条</a></dt>
                <dd><a href="javascript:void(0)">内部请求：10条</a></dd>
                <dd><a href="javascript:void(0)">协作工单：8条</a></dd> --> 
            </dl>
        </div>
    </div>
    <div class="middle-wrap">
        <img class="ad" src="dist/img/banner.png" width="480" height="166" alt="广告">
    </div>
    <div class="right-wrap">
        <div class="ann-wrap">
            <div class="mod-hd">
                <h2>公告</h2>
                <a class="more" href="" target="_blank">更多</a>
            </div>
            <div class="mod-bd">
                <ul>
                    <li><a href="javascript:void(0)" target="_blank">2016年活动板房产品供应商招标公告</a></li>
                    <li><a href="javascript:void(0)" target="_blank">2016年第一次开关电源产品供应商招标公告</a></li>
                    <li><a href="javascript:void(0)" target="_blank">专项审计中介机构招标公告</a></li>
                    <li><a href="javascript:void(0)" target="_blank">在线商务平台招标公告</a></li>
                </ul>
            </div>
        </div>
    </div>
</section>

<section class="main-wrap">
    <div class="left-wrap">
        <div class="active-wrap work-wrap">
            <div class="mod-hd">系统项目</div>
            <div class="mod-bd">
                <ul class="clearfix">
           
                    <li>
                        <a href="javascript:iframeshow(1);"   title="">
                            <figure>
                                <img src="dist/img/icon_email.png" alt="">
                                <figcaption>资源管理<br/>(后台管理)</figcaption>
                            </figure>
                        </a>
                    </li>
                 
                       <li>
                        <a href="javascript:iframeshow(4);"   title="">
                            <figure>
                                <img src="dist/img/icon_email.png" alt="">
                                <figcaption>资源管理<br/>(业务平台)</figcaption>
                            </figure>
                        </a>
                    </li>
                             <li>
                        <a href="javascript:iframeshow(2);"   title="">
                            <figure>
                                <img src="dist/img/icon_bangoung.png" alt="">
                                <figcaption>资产核查<br/>(郑州铁塔)</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:iframeshow(3);"   title="">
                            <figure>
                                <img src="dist/img/icon_meeting.png" alt="">
                                <figcaption>门户系统管理</figcaption>
                            </figure>
                        </a>
                    </li>
                    <!--  
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_tongxunlu.png" alt="">
                                <figcaption>通讯录</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_wupin.png" alt="">
                                <figcaption>物品领用</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/iocn_che.png" alt="">
                                <figcaption>用车申请</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_task.png" alt="">
                                <figcaption>任务督办</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_weixiu.png" alt="">
                                <figcaption>维修申请</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_beiwang.png" alt="">
                                <figcaption>工作备忘</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_gonghui.png" alt="">
                                <figcaption>工会活动</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_caipu.png" alt="">
                                <figcaption>每周食谱</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_dinggou.png" alt="">
                                <figcaption>物品订购</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="">
                            <figure>
                                <img src="dist/img/icon_yuangong.png" alt="">
                                <figcaption>员工关怀</figcaption>
                            </figure>
                        </a>
                    </li>
                    -->
                </ul>
            </div>
        </div>
    </div>
    <div class="middle-wrap">
        <div class="news-wrap">
            <div class="mod-hd">
                <a class="more" href="" target="_blank">更多</a>
                <ul>
                    <li class="active">省内新闻</li>
                    <li>行业相关</li>
                    <li>简报</li>
                </ul>
            </div>
            <div class="mod-bd">
                <ul>
                    <li><a href="javascript:void(0)">2016年第二批配套设备模块化安装工程服务商集中招标公告</a><time>08-10</time></li>
                    <li><a href="javascript:void(0)"> 铁塔在线商务平台2016年第一次小型油机产品招标公告</a><time>08-05</time></li>
                    <li><a href="javascript:void(0)"> 地铁6号线（一期）室内分布系统工程监理服务中标结果</a><time>08-01</time></li>
                    <li><a href="javascript:void(0)">2016年普通铅酸蓄电池供应商认证招标公告</a><time>07-20</time></li>
                    <li><a href="javascript:void(0)"> 2016年度铁塔产品供应商认证招标公告</a><time>07-15</time></li>
                </ul>
            </div>
        </div><!-- /.news-wrap -->

        <div class="news-wrap mt10">
            <div class="mod-hd">
                <a class="more" href="" target="_blank">更多</a>
                <ul>
                    <li class="active">全国新闻</li>
                    <li>电子周刊</li>
                    <li>经济新闻</li>
                    <li>社会新闻</li>
                </ul>
            </div>
            <div class="mod-bd">
                <ul>
                    <li><a href="javascript:void(0)"> 2016年新建通信铁塔接入机房和铁塔塔基土建施工预中标</a><time>08-10</time></li>
                    <li><a href="javascript:void(0)">2016年度基站舒适空调供应商招标公告</a><time>08-02</time></li>
                    <li><a href="javascript:void(0)"> 2016年配套综合柜产品供应商认证招标公告</a><time>08-01</time></li>
                    <li><a href="javascript:void(0)"> 2016年度大型设计供应商招标公告</a><time>07-25</time></li>
                    <li><a href="javascript:void(0)">室内分布系统工程设计服务中标结果</a><time>07-16</time></li>
                </ul>
            </div>
        </div><!-- /.news-wrap -->
    </div>
    <div class="right-wrap">
        <div class="active-wrap">
            <div class="mod-hd">专题活动</div>
            <div class="mod-bd">
                <ul class="clearfix">
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="优秀员工">
                            <figure>
                                <img src="dist/img/banner1.png" alt="优秀员工">
                                <figcaption>优秀员工</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="工会活动">
                            <figure>
                                <img src="dist/img/banner2.png" alt="工会活动">
                                <figcaption>工会活动</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="法律法规">
                            <figure>
                                <img src="dist/img/banner3.png" alt="法律法规">
                                <figcaption>法律法规</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="两学一做">
                            <figure>
                                <img src="dist/img/banner4.png" alt="两学一做">
                                <figcaption>两学一做</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="安全主题月">
                            <figure>
                                <img src="dist/img/banner5.png" alt="安全主题月">
                                <figcaption>安全主题月</figcaption>
                            </figure>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" target="_blank" title="工作与创新">
                            <figure>
                                <img src="dist/img/banner6.png" alt="工作与创新">
                                <figcaption>工作与创新</figcaption>
                            </figure>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</section>

<footer class="foot-wrap">
    <p><span>ALL rights reserved China Tower 中国铁塔</span> <span>推荐使用谷歌、火狐、360、猎豹浏览器</span></p>
</footer>

</body>
</html>
