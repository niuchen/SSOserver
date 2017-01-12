<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>	

<jsp:include page="/inc1.4.5.jsp"></jsp:include>
<script src="<%=basePath%>js/layer-v2.3/layer/layer.js"></script>
<script type="text/javascript">
			var queryparams = {};
			//查询方法
			function queryList(){
				//var v_role_name = $("#selectRoleSels").combobox('getValue');
			//	queryparams.tag=1;$('#upv_login_name').val(row.v_login_name);
			//	queryparams.q=v_role_name;
				  $('#list_data').datagrid("options").url= "<%=basePath%>selectuser.action?i_type=1";
				// serializeObject 在util.js中 将form表单元素的值序列化成对象
			 	 $('#list_data').datagrid('load', serializeObject($('#selectform')));
 			}
			
 
 		 	//添加弹出框打开
	  		function openAddUser(){
 				$('#adduserWin').window('open');
			}
				//添加弹框提交
			function commitAdduser(){
				try{
					layer.load();
					var tag = $('#adduserForm').form('validate');
					if(tag){
						$('#adduserForm').form('submit',{
								url:'<%=basePath%>insertuser.action',
							onSubmit : function(param) {
 							 //alert($('#v_company_name').combo('getText'));
							 param.v_company_name2= $('#v_company_name').combo('getText');  
							//   return false;
							},  
							success : function(result) {
								var reObj = $.parseJSON(result);
								if (reObj.op.result == 1) {
									layer.closeAll();
									layer.msg('添加成功！');
									$('#adduserForm').form('reset');
									$('#adduserWin').window('close');
									queryList();
								} else if (reObj.op.resul != 1) {
									layer.closeAll();
									layer.msg('添加失败! ' 
											+ reObj.op.reuslt_hint);
								}
							}
						});
			} else {
				layer.closeAll();
			}

		} catch (e) {
			alert(e);
		}

	}
	
	
	
		//修改弹出框打开
	  		function openupUser(){
	  		if($('#list_data').datagrid('getRows').length == 0){
		    		layer.msg('请先进行数据查询！');
		    		return ;
		    	}
		    	var row = $('#list_data').datagrid('getSelected');
		    	if(!row){
		    		layer.msg('请选择一条记录！');
		    		return ;
		    	}
		     //表单控制值
//  		    	$('#upv_login_name').val(row.v_login_name);
// 		    	$('#upv_login_password').val(row.v_login_password);
// 		    	$('#upv_real_name').val(row.v_real_name);
// 		    	$('#upv_user_tel').val(row.v_user_tel);
// 		    	$('#upi_user_id').val(row.i_user_id);
		    	
			//表单展示值
				 $('#upuserForm').form('load',{
					upv_login_name:row.v_login_name,
					//upv_login_password:row.v_login_password,
					upv_real_name:row.v_real_name,
					upv_user_tel:row.v_user_tel,
					upi_user_id:row.i_user_id,
					upv_company_name:row.i_subcomp_id,
					upi_admin_tag	:row.i_admin_tag,
					upis_area_manager:	row.is_area_manager
				});
				//alert(row.i_subcomp_id);
				//$('#upv_company_name').combo('setValue',row.i_subcomp_id);
				$('#upuserWin').window('open');
			}
				//修改弹框提交
			function commitupuser(){
				layer.load();
				var tag = $('#upuserForm').form('validate');
				if(tag){
					$('#upuserForm').form('submit',{
						url:'<%=basePath%>updateuser.action',
 						onSubmit : function(param) {
 						//将选择的地市名称也当做参数传递
  							 param.v_company_name2= $('#upv_company_name').combo('getText');  
 							}, 
						success: function(result){
 						var res=$.parseJSON(result);
 						// alert(res.OperateState.result);
 							if(res.op.result==1){
								layer.closeAll();
								layer.msg('修改成功！'+res.op.reuslt_hint);
 									var row = $('#list_data').datagrid('getSelected');
									var rowindex=$('#list_data').datagrid('getRowIndex',row);
 								     $('#list_data').datagrid('updateRow',{
										index: rowindex,
										row: {
 											v_login_name: res.op.reuslt_value.v_login_name,
										//	v_login_password: res.op.reuslt_value.v_login_password,
											v_real_name: res.op.reuslt_value.v_real_name,
											v_user_tel: res.op.reuslt_value.v_user_tel,
											i_user_id: res.op.reuslt_value.i_user_id,
											v_company_name:res.op.reuslt_value.v_subcomp_name,
											i_admin_tag:res.op.reuslt_value.i_admin_tag,
											is_area_manager:res.op.reuslt_value.is_area_manager
										}
									});
								$('#upuserForm').form('reset');
  								$('#upuserWin').window('close');
							}else {
								layer.closeAll();
								layer.msg('修改失败'+res.op.reuslt_hint,{
								//  icon: 1,
								  time: 10000 //2秒关闭（如果不配置，默认是3秒）
								});
							}
						
						}
					});
					}
			}
			
			//启用禁用
			function Enable_disableuser(){
				if($('#list_data').datagrid('getRows').length == 0){
		    		layer.msg('请先进行数据查询！');
		    		return ;
		    	}
		    	var row = $('#list_data').datagrid('getSelected');
		    	if(!row){
		    		layer.msg('请选择一条记录！');
		    		return ;
		    	}
		    	var str='';
 				if(row.i_delete_state==0){
				  str="禁用";
				}else if(row.i_delete_state==1){
				   str="启用";
				}
				layer.confirm("是否确认"+str+"账号?", {
				  btn: ['确定','取消'] 
				}, function(){
					var str; 
				 	var row = $('#list_data').datagrid('getSelected');
				 	if(row.i_delete_state==0){
					  str=1;
					}else if(row.i_delete_state==1){
					   str=0;
					}
					$.ajax({
		  				url:'<%=basePath%>Enable_disableuser.action',
		  				dataType:'json',
		  				data:{
		  					i_delete_state:str,
		  					i_user_id:row.i_user_id
		  				},
		  				type:'POST',
		  				success:function(data){	
		  				//var res=$.parseJSON(data);
		  				var res=data;
 						// alert(res.OperateState.result);
 							if(res.op.result==1){
 								layer.closeAll();
 								layer.msg('修改成功！'+res.op.reuslt_hint);
 								queryList();
		  				    }else {
		  				 
		  				  		  layer.closeAll();
 								layer.msg('修改失败'+res.op.reuslt_hint,{
								  icon: 1,
								  time: 10000 //2秒关闭（如果不配置，默认是3秒）
								});
							}
		  				}
		  			});
				});
			}
			//删除角色
            function deleteuser(){
				if($('#list_data').datagrid('getRows').length == 0){
		    		layer.msg('请先进行数据查询！');
		    		return ;
		    	}
		    	var row = $('#list_data').datagrid('getSelected');
		    	if(!row){
		    		layer.msg('请选择一条记录！');
		    		return ;
		    	}
				
				layer.confirm('确定要删除此账号吗？注:永久删除,无法恢复!', {
				  btn: ['确定','取消'] 
				}, function(){
					$.ajax({
		  				url:'<%=basePath%>deleteusers.action',
		  				dataType:'json',
		  				data:{
		  					i_user_id:row.i_user_id
		  				},
		  				type:'POST',
		  				success:function(data){	
		  				//var res=$.parseJSON(data);
		  				var res=data;
 						// alert(res.OperateState.result);
 							if(res.op.result==1){
 								layer.closeAll();
 								layer.msg('修改成功！'+res.op.reuslt_hint);
 								queryList();
		  				    }else {
		  				  		  layer.closeAll();
 								layer.msg('修改失败'+res.op.reuslt_hint);
							}
		  				}
		  			});
				});
 			}
//-->
</script>


<form id="selectform" method="post">
	<div id="p" class="easyui-panel" data-options=" " align="left">
		<label>账号名称：</label> <input id="selectRoleSels" name="selectRoleSels"
			class="easyui-combogrid"
			data-options="
							    		idField: 'v_role_name',
										textField: 'v_role_name',
										//url:'<%=basePath%>',
										method: 'post',
									 	mode: 'remote',
										width:'120px',
										onBeforeLoad:function(param){
										},
										panelWidth: 500,
										loadMsg:'正在加载数据...',
										columns: [[
											{field:'v_role_name',title:'角色名称',width:80},
											{field:'v_role_desc',title:'角色描述',width:120}
										]],
										fitColumns: true,
										pagination:true
						    		" />
		<a href="javascript:void(0);" style="float: right;"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel',plain:true"
			onclick="$('#selectform').form('clear');">清空</a> <a
			href="javascript:void(0);" style="float: right;"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-search',plain:true"
			onclick="queryList();">查询</a>

	</div>
</form>

<div id="tb">
	<c:if test="${addBtn != 1 }">
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-add'" onclick="javascript:openAddUser();">新增</a>
	</c:if>

	<c:if test="${modifyBtn != 1 }">
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" onclick="javascript:openupUser();">修改</a>
	</c:if>
<!-- 
	<c:if test="${deleteBtn != 1 }">
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove'"
			onclick="javascript:deleteuser();">删除</a>
	</c:if>-->
	<c:if test="${deleteBtn != 1 }">
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove'"
			onclick="javascript:Enable_disableuser();">启用/禁用</a>
	</c:if>
</div>


<table id="list_data" class="easyui-datagrid"
	data-options="	fit:false,
				fitColumns:true,
				singleSelect:true,
				pagination:true,
				pageNumber:0,
				pageSize:20,
				rownumbers:true,
				loadMsg:'正在加载，请稍候',
				width : 'auto',
			    height : '400',
				toolbar:'#tb'
 			">
	<thead>
		<tr>
		
		 
			<th data-options="field:'v_company_name',align:'center',width:80">地市</th>
			<th data-options="field:'i_user_id',align:'center',width:80">账号编号</th>
			<th data-options="field:'v_login_name',width:100">账号登陆名</th>
			<th data-options="field:'v_real_name',align:'center',width:120">账号人姓名</th>
			<!--  <th data-options="field:'v_login_password',align:'center',width:120">密码</th>-->
			<th data-options="field:'v_user_tel',align:'center',width:120">手机号</th>
			<th data-options="field:'i_admin_tag',align:'center',width:120,formatter:function(v,row,idx){
							   if(v==0){return '否';}
							    if(v==1){return '是';}
								return v;
							}">是否超级管理员</th>
			<th data-options="field:'is_area_manager',align:'center',width:120,formatter:function(v,row,idx){
							   if(v==0){return '否';}
							    if(v==1){return '是';}
								return v;
							}">是否地市管理员</th>
			
			<th
				data-options="field:'i_delete_state',width:140,align:'center',formatter:function(v,row,idx){
							   if(v==0){return '启用';}
							    if(v==1){return '禁用';}
								return v;
							}">
				是否启用</th>

		</tr>
	</thead>
</table>


<!-- 新增角色层 -->
<div id="adduserWin" class="easyui-window" title="新增用户"
	data-options="iconCls:'icon-save',top:10 ,
		draggable:true,resizable:false,modal:true,minimizable:false,
		closed:true,maximizable:false,border:false,collapsible:false"
	style="display: none;width:460px;height:330px; padding: 0px; ">
	<div class="easyui-panel" style="width:100%;height:260px;">
		<form id="adduserForm" class=easyui-form " method="post">
			<table class="form-table_tab" cellpadding="5" width="100%">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
				</colgroup>
				<tbody>
					<tr>
						<td class="tdcls">地市<font color="red">*</font>：</td>
						<td><input id="v_company_name" class="easyui-combobox"
							name="v_company_name"
							data-options="valueField:'i_company_id',
							editable:false,
							value:0,
							 required:true ,
							textField:'v_company_name',url:'CompanyController/selectcompany.action'" />
						</td>
					</tr>
					<tr>
						<td class="tdcls">账户登陆名<font color="red">*</font>：</td>
						<td><input id="v_login_name" name="v_login_name"
							class="easyui-textbox"
							data-options="required:true,validType:'length[1,11]'" />
						</td>
					</tr>
					<tr>
						<td class="tdcls">账户密码<font color="red">*</font>：</td>
					 
						<td><input id="v_login_password" name="v_login_password"
							class="easyui-textbox"
							data-options="required:true,validType:'length[6,10]'" />
						</td>
					 
					</tr>
 					<tr>
						<td class="tdcls">账户用户名称<font color="red">*</font>：</td>
				 
						<td><input id="v_real_name" name="v_real_name"
							class="easyui-textbox"
							data-options="required:true,validType:'length[2,10]'" />
				   
						</td>
					</tr>
					<tr>
						<td class="tdcls">账户用户手机号<font color="red">*</font>：</td>
				 
						<td><input id="v_user_tel" name="v_user_tel"
							class="easyui-textbox"
							data-options="required:true,validType:'length[11,11]'" />
 						</td>
					</tr>
					<tr>
						<td class="tdcls">是否超级管理员<font color="red">*</font>：</td>
						
						<td>
						<select id="i_admin_tag"  class="easyui-combobox," 
						data-options="editable:false,required:true"
						name="i_admin_tag" style="width:200px;">   
					    <option value="0" selected="selected">否</option>   
					    <option value="1">是</option>   
						 </select>  </td>
 					</tr>
					<tr>
						<td class="tdcls">是否地市管理员<font color="red">*</font>：</td>
						<td>	
						<select id="is_area_manager" class="easyui-combobox"
						  data-options="editable:false,required:true" name="is_area_manager" style="width:200px;">   
					    <option value="0" selected="selected">否</option>   
					    <option value="1">是</option>   
						 </select> 
 						</td>
					</tr>
					
				</tbody>
			</table>
		</form>
	</div>
	<div style="text-align:right;padding:1px 5px 0 0;margin-top:5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:commitAdduser()"
			style="width:80px">提交</a>
	</div>
</div>




<!-- 修改角色层 -->
<div id="upuserWin" class="easyui-window" title="修改用户"
	data-options="iconCls:'icon-save',top:10,
		draggable:true,resizable:false,modal:true,minimizable:false,
		closed:true,maximizable:false,border:false,collapsible:false"
	style="display: none;width:460px;height:330px; padding: 0px; ">
	<div class="easyui-panel" style="width:100%;height:260px;">
		<form id="upuserForm" class=easyui-form " method="post">
			<table class="form-table_tab" cellpadding="5" width="100%">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
				</colgroup>
				<input type="hidden" name="upi_user_id" id="upi_user_id"/>
				<tbody>
					<tr>
						<td class="tdcls">地市<font color="red">*</font>：</td>
						<td>
					<input id="upv_company_name" class="easyui-combobox" name="upv_company_name"   
					    data-options="valueField:'i_company_id',
					    editable:false,
					    textField:'v_company_name',url:'CompanyController/selectcompany.action',
					    required:true 
					    " />  
 
						</td>
					</tr>
					<tr>
						<td class="tdcls">账户登陆名<font color="red">*</font>：</td>
						<td><input id="upv_login_name" name="upv_login_name"
							class="easyui-textbox"
							data-options="required:true,validType:'length[1,11]'" />
						</td>
					</tr>
					<!-- 
					<tr>
						<td class="tdcls">账户密码<font color="red">*</font>：</td>
					 
						<td><input id="upv_login_password" name="upv_login_password"
							class="easyui-textbox"
							data-options="required:true,validType:'length[6,10]'" />
						</td>
					 
					</tr> -->
 					<tr>
						<td class="tdcls">账户用户名称<font color="red">*</font>：</td>
				 
						<td><input id="upv_real_name" name="upv_real_name"
							class="easyui-textbox"
							data-options="required:true,validType:'length[2,10]'" />
				   
						</td>
					</tr>
					<tr>
						<td class="tdcls">账户用户手机号<font color="red">*</font>：</td>
				 
						<td><input id="upv_user_tel" name="upv_user_tel"
							class="easyui-textbox"
							data-options="required:true,validType:'length[11,11]'" />
 						</td>
					</tr>
						<tr>
						<td class="tdcls">是否超级管理员<font color="red">*</font>：</td>
						
						<td>
						<select id="upi_admin_tag" class="easyui-combobox" name="upi_admin_tag" style="width:200px;">   
					    <option value="0" selected="selected">否</option>   
					    <option value="1">是</option>   
						 </select>  </td>
 					</tr>
					<tr>
						<td class="tdcls">是否地市管理员<font color="red">*</font>：</td>
						<td>	
						<select id="upis_area_manager" class="easyui-combobox" name="upis_area_manager" style="width:200px;">   
					    <option value="0" selected="selected">否</option>   
					    <option value="1">是</option>   
						 </select> 
 						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div style="text-align:right;padding:1px 5px 0 0;margin-top:5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:commitupuser()"
			style="width:80px">提交</a>
	</div>
</div>