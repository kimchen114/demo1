package com.example.ms_demo.alipay.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class AlipayUtil {
	//获取支付宝POST过来反馈信息
	public static Map<String,String>  getParams(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		if(null == request){
		    return params;
		}
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Map.Entry<String,String[]> requestParam : requestParams.entrySet()) {
			String name = requestParam.getKey();
			String[] values = requestParam.getValue();
			String valueStr = StringUtils.join(values, ",");
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
    }
}
