/**
 * @author sunzexing
 * <p>标题：</p>
 * <p>描述：</p>
 * 2016-4-15
 *
 */
package com.SSOserver.common.utlt;

import org.springframework.util.StringUtils;

 


/**
 * @author sunzexing
 * <p>标题：</p>
 * <p>描述：</p>
 * 2016-4-15
 */
public class StringUtil {

	
	/**
	 * @author sunzexing
	 * <p>标题：</p>
	 * <p>描述：</p>
	 * 2016-4-15
	 * return boolean
	 */
	public static  boolean isEmpty(String str){
		
		boolean result = false;
		
		if(StringUtils.isEmpty(str)){
			return true;
		}else if("".equals(str.trim())){
			return true;
		}else if("undefined".equals(str.trim())){
			return true;
		}
		
		return result;
	} 
}
