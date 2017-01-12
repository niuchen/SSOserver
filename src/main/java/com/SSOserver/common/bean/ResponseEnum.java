package com.SSOserver.common.bean;

/**
 * 结果类型
 * @author sunjuncai
 * @date 2015-6-18
 */
public enum ResponseEnum {
	/** 请求空数据  */
	EMPTY(3001), 
	
	/** 请求成功  */
	SUCCESS(3002),
	
	/** 请求失败  */
	FAILURE(3003),
	
	/** 请求错误   */ 
	ERROR(3004),
	
	/** JSON解析错误 */
	JSONException(3005);
	
	private int _id;
	
	ResponseEnum(int id) {
		_id = id;
	}

	public int getId() {
		return _id;
	}
}
