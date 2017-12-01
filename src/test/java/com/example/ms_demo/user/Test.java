package com.example.ms_demo.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
	
	   System.out.println(new BCryptPasswordEncoder().encode("123456")) ;
	    
	    
	}
	
	
	@SuppressWarnings("unchecked")
	public static <K,T> Map<K,T> extractToMap(Collection<T> collection, String key) {
	        Map<K,T> map = new HashMap<K,T>(collection.size());
	        try {
	            for (T obj : collection) {
	                map.put((K)PropertyUtils.getProperty(obj, key),obj);
	            }
	        } catch (Exception e) {
	        	System.err.println(e);
	        }

	        return map;
	    }
}
