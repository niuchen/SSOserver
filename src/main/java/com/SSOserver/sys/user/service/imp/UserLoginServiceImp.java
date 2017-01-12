package com.SSOserver.sys.user.service.imp;

 
 

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.IdGenerator;

import com.SSOserver.common.bean.OperateState;
import com.SSOserver.common.bean.T_MODULES;
import com.SSOserver.common.bean.T_USERS_INFO;
import com.SSOserver.sys.user.dao.IUserLoginDao;
import com.SSOserver.sys.user.pojo.EaysUiTreepojo;
import com.SSOserver.sys.user.service.IuserLoginService;
 
/***
 * 用户管理相关的服务接口  
 * 其中还包括账号与子系统账号的同步创建.
 * niuchen
 * 2016-9-8
  * ***/
@Service("UserLogServiceImp")
public class UserLoginServiceImp     implements IuserLoginService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Resource(name = "sqlSessionFactory")
	public SqlSessionFactory sf;
	
 	@Resource(name = "transactionManager") 
 	public DataSourceTransactionManager txManager;
	
// 	   @Resource(name="DefaultIdGenerator")
// 	  public	IdGenerator idGenerator;
//	 	public void setIdGenerator(IdGenerator idGenerator) {
//	 		this.idGenerator = idGenerator;
//	 	}
	 	 @Resource(name="IUserLoginDao")
	 	IUserLoginDao iuserlogindao;
	 	 
	 	@Resource(name="UserSubsystem")
	 	UserSubsystem usersubsystem;
		@Override
		public List<EaysUiTreepojo> sysmoduletree() {
			Map<String, Object> m=new HashMap<String, Object>();
			m.put("i_del_tag",2);
//  		    List<T_MODULES> list=	iuserlogindao.sysmoduletree(m);
//  		    List<EaysUiTreepojo> eaylist=new ArrayList<EaysUiTreepojo>();
// 			for(T_MODULES tm:list){
//				EaysUiTreepojo ep=new EaysUiTreepojo();
//				ep.setId(tm.getM_id()+"");
//				ep.setText(tm.getM_name());
//				ep.setParent_id(tm.getM_parent_id()+"");
//			    Map<String,String> attrebutes = new HashMap<String,String>();
//				attrebutes.put("url", tm.getM_file_path());
// 			    ep.setAttributes(attrebutes) ;
//				ep.setIconCls(tm.getM_iconcls());
//	//			System.out.println(ep.getText());
//				eaylist.add(ep);
// 			}
// 			// 		   // ep.setState("closed");
 	  	     
 			List<EaysUiTreepojo> elist=new ArrayList<EaysUiTreepojo>();
  		     EaysUiTreepojo e=new EaysUiTreepojo();
			e.setId("0");
 			e.setText("功能");
 		
 			List<EaysUiTreepojo> userelist=new ArrayList<EaysUiTreepojo>();
 			EaysUiTreepojo usere=new EaysUiTreepojo(); 
 			usere.setId("1");
 			usere.setText("账号管理");
 			usere.setParent_id("0");
	 			Map<String,String> attrebutes = new HashMap<String,String>();
				attrebutes.put("url", "selectuserlist.action");
			usere.setAttributes(attrebutes) ;
			userelist.add(usere);
 			e.setChildren(userelist);
 			
 			elist.add(e);
			return elist;
		}
	 
		/**
	     *递归树节点下级. 根据根节点找到全部的子节点.
	     *m全部节点的集合
	     *eay根节点对象   会向对象存储下级节点并返回.
	     * **/
		public EaysUiTreepojo	orderTree(List<EaysUiTreepojo> eaylist,EaysUiTreepojo eay){
			//EaysUiTreepojo eay=new EaysUiTreepojo();
			int i=0;
 		 for(EaysUiTreepojo e:eaylist){
  			 if(e.getParent_id().equals(eay.getId())){
 				i++;
 				//System.out.println(e.getParent_id());
 				eay.getChildren().add(e);
 				orderTree(eaylist,e);//递归调用
 			 }
 		 }
			if(i==0){
				//System.out.println(eay.getId()+" 没有找到下级节点..");
				return null;
			}
			//System.out.println(m.get(rootid).getId());
			return eay;
		}
		
		
		
		/**
		 * 查询用户列表
		 * **/
		public List<T_USERS_INFO> selectuser(Map m){
 			 
			List<T_USERS_INFO> list=iuserlogindao.selectuser(m);
			return list;
		}
 		public Integer selectuserCnt(Map m){
			Integer cnt=iuserlogindao.selectuserCnt(m);
		//	System.out.println(cnt);
			return cnt;
		}
 		public T_USERS_INFO getuserlogin(Map m){
 			
			T_USERS_INFO user=iuserlogindao.getuserlogin(m);
			return user;
 		}
 		
 		public	List<T_USERS_INFO> selectuserwhere(Map m){
 			List<T_USERS_INFO> list=iuserlogindao.selectuserwhere(m);
			return list;
 		}
 		public Integer selectuserseq(){
 		   return	iuserlogindao.selectuserseq();
 		}
 		/**
 		 * 添加账户 并同步子系统
 		 * **/
 		public OperateState insertuser(T_USERS_INFO user){
 				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	 			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	 			TransactionStatus status = txManager.getTransaction(def);
	 			OperateState op=null;
	 			OperateState op2= null;
 			try {
 				//HTTP执行子系统同步账号 郑州铁塔系统
 				  op= usersubsystem.tower_user_insert(user);
  				  op2= usersubsystem.towerpor_user_insert(user);
   				
  				/**事务处理  异常或失败删除回滚 如果成功就主系统添加账号**/
 				if(op.getResult()==1&&op2.getResult()==1){//如果同步成功
 					iuserlogindao.insertuser(user);//主系统添加
 					txManager.commit(status);
 					return new OperateState(1, null, "成功", null);
 				}else{//同步有一个失败.
 					txManager.rollback(status);
 					shiwudelete(op, op2, user);//事务处理  回滚删除已经添加的数据
  					return new OperateState(2, null, "子系统账户同步过程中失败!请联系管理员!子项目1郑州老铁塔:"+op.getReuslt_hint()+"子项目2资源管理:"+op2.getReuslt_hint(), null);
 				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				txManager.rollback(status);
				shiwudelete(op, op2, user);//事务处理  回滚删除已经添加的数据
				logger.error("添加账户-HTTP子系统接口调用异常:"+e.getMessage());
 				return new OperateState(3, e.getMessage(), "失败"+e.getMessage(), null);
			}
 		}
 		/***
 		 * 事务处理两个子系统的接口数据.   如果有错误异常.  就删除已经添加的账号.
 		 * ***/
 		private void  shiwudelete(OperateState op,OperateState op2,T_USERS_INFO user){
 			
 			if(op!=null){
 				if(op.getResult()==1){
					OperateState deleteop=usersubsystem.tower_delete_user(user.getI_user_id(),user.getV_login_name());
					if(deleteop.getResult()!=1){
						logger.error("tower子项目同步删除用户账号失败:"+deleteop.getReuslt_hint());
					}else{
						logger.info("tower子项目同步删除用户账号成功"+user.getI_user_id()+op.getReuslt_hint());
					}
				}
 			}
 			if(op2!=null){
				if(op2.getResult()==1){
					OperateState deleteop=usersubsystem.towerpro_delete_user(user.getI_user_id(),user.getV_login_name());
					if(deleteop.getResult()!=1){
						logger.error("towerpro子项目同步删除用户账号失败:"+deleteop.getReuslt_hint());
					}else{
						logger.info("towerpro子项目同步删除用户账号成功"+user.getI_user_id()+op.getReuslt_hint());
					}
				}
 			}
 		}
 		
 		/***更新用户信息. 并同步到子系统中**/
 		 public OperateState updateusers(T_USERS_INFO user){
  			try {
 				int i=iuserlogindao.updateusers(user);
 				String err="";
 				OperateState op1=usersubsystem.tower_user_update(user);
  				OperateState op2=usersubsystem.towerpor_user_uodate(user);
 				if(op1.getResult()!=1){
 					err="(tower没有同步成功:返回状态"+op1.getResult()+"-"+op1.getReuslt_hint()+op1.getReuslt_err()+")";
 				}
 				if(op2.getResult()!=1){
 					err+="(towerpor没有同步成功:返回状态"+op2.getResult()+"-"+op1.getReuslt_hint()+op2.getReuslt_err()+")";
 				}
 				if(!err.equals("")){
 					return new OperateState(2, null,"主程序更新成功!请联系管理员,修改操作失败!此刻主程序与子程序的用户数据是不同步的!"+err+"", null);
 				}
 					
				return new OperateState(1, null, "成功更新记录条数:"+i, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("更新异常:"+e.getMessage());
				return new OperateState(3, e.getMessage(), "失败"+e.getMessage(), null);
			}
 		 }
 		 
 		 public OperateState deleteusers(Map m){
 			try {
 				int i=iuserlogindao.deleteusers(m);
				return new OperateState(1, null, "成功!记录条数:"+i, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("删除异常:"+e.getMessage());
				return new OperateState(3, e.getMessage(), "失败"+e.getMessage(), null);
			}
 		 }
 		 
 		public OperateState Enable_disableuser(Map m){
 			 
 			try {
 				int i=iuserlogindao.Enable_disableuser(m);
 				return new OperateState(1, null, "成功!记录条数:"+i, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
 				logger.error("启用禁用异常:"+e.getMessage());
				return new OperateState(3, e.getMessage(), "失败"+e.getMessage(), null);
			}
 		}

		@Override
		public OperateState updatepaw(String i_user_id, String uppaw) {
			Map map=new HashMap();
 			map.put("v_login_password", uppaw);
			map.put("i_user_id", i_user_id);
			 try {
				iuserlogindao.updateuserpaw(map);
				 return new OperateState(1, null, "修改成功!", null);
			} catch (Exception e) {
 				e.printStackTrace();
				return new OperateState(3, e.getMessage(), "异常!"+e, null);
			}
			 
		}

		@Override
		public T_USERS_INFO selectdatepaw(String v_login_name, String paw)   {
			try {
				Map map=new HashMap();
				map.put("i_delete_state", 0);
				map.put("v_login_password", paw);
				map.put("v_login_name", v_login_name);
				T_USERS_INFO tuser=iuserlogindao.getuserlogin(map);
				 return tuser;
			} catch (Exception e) {
  				e.printStackTrace();
 				throw e;
			}
			
		}
 }
