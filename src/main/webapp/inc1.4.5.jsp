
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="favicon.ico"> 
 <meta name="author" content="">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.5/jquery.min.js" charset="utf-8"></script>
<!-- jquery Cookie插件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" charset="utf-8"></script>
<!-- easyui相关库 -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.5/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.5/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/common.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="utf-8"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js" charset="utf-8"></script>

<script>
 

//自定义验证规则.  验证两个日期空间时间 起与止 
$.extend($.fn.validatebox.defaults.rules,{
   TimeCheck:{
    validator:function(value,param){  
      var s = $("#"+param[0]).datebox("getValue"); 
 	    if(param[1]==2){
	      //因为日期是统一格式的所以可以直接比较字符串 否则需要Date.parse(_date)转换
	         if(s==''||s==null){
	             var curr_time = new Date(value);
				   var strDate = curr_time.getFullYear()+"-";
				   strDate += curr_time.getMonth()+1+"-";
				   strDate += curr_time.getDate()+" ";
				   strDate += curr_time.getHours()+":";
				   strDate += curr_time.getMinutes()-1+":";
				   strDate += curr_time.getSeconds();
				   alert(strDate);
 	         	   $("#"+param[0]).datetimebox("setValue", strDate); 
	          	   return true;
	         }
	     return value>=s;
	    }  else if(param[1]==1){
		    if(s==''||s==null){
	 		   $("#"+param[0]).datetimebox("setValue", value); 
		      return true;
		    }
	       return value<s;
	    }else{
	       return false;
	    }
     },
    message:'非法数据'
   }
   });
  
  
 
</script> 
  