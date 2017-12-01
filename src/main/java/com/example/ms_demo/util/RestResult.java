package com.example.ms_demo.util;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class RestResult implements Serializable{

	
	private Object result;
	
	private Boolean isSuccess;
	
	private String errorMsg;
	
	private Map<String,Object> results;
	
	
	public RestResult(){
		this.isSuccess=true;
	}
	
	public static RestResult succsee(){
		RestResult r = new RestResult();
		r.isSuccess=true;
		return r;
	}
	public static RestResult succsee(Object result){
		RestResult r = new RestResult();
		r.isSuccess=true;
		r.result=result;
		return r;
	}
	public static RestResult failed(String errorMsg){
		RestResult r = new RestResult();
		r.isSuccess=false;
		r.errorMsg=errorMsg;
		return r;
	}
	
	
	public RestResult(String errorMsg){
		this.isSuccess=false;
		this.errorMsg=errorMsg;		
	}
	

	
	
	
}
