
package com.bj58.web.api.utils;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author GuoYuLiang
 *
 * 2018年1月27日
 */
public class HttpServletRequestUtils extends org.springframework.web.bind.ServletRequestUtils{
	
	public static  Map<String, String> getHeadersMap(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String value = request.getHeader(name);
			headers.put(name, value);
		}
		return headers;
	}
	
	public static Map<String, String> getParamsMap(ServletRequest servletRequest) {
		Map<String, String[]> map = servletRequest.getParameterMap();
		Map<String, String> param = new HashMap<String, String>();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String[] values = map.get(key);
			if (values != null) {
				if(values.length == 1)
				{
					param.put(key, values[0]);
				}else
				{
					param.put(key, Arrays.toString(values));
				}
			}
		}
		return param;
	}

}
