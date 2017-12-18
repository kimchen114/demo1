package com.example.ms_demo.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Conventions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class RestResult implements Serializable {
    
    // private Object result;
    
    private Boolean isSuccess;
    
    private String errorMsg;
    
    private Map<String, Object> result = new HashMap<String, Object>();
    
    public RestResult() {
        this.isSuccess = true;
    }
    
    public static RestResult succsee() {
        RestResult r = new RestResult();
        r.isSuccess = true;
        return r;
    }
    
    public static RestResult succsee(Object result) {
        RestResult r = new RestResult();
        r.isSuccess = true;
        r.result.put(Conventions.getVariableName(result), result);
        return r;
    }
    
    public static RestResult failed(String errorMsg) {
        RestResult r = new RestResult();
        r.isSuccess = false;
        r.errorMsg = errorMsg;
        return r;
    }
    
    public RestResult(String errorMsg) {
        this.isSuccess = false;
        this.errorMsg = errorMsg;
    }
    
    public Object putKey(String key, Object value) {
        return result.put(key, value);
    }
    
    public Object putKey(Object value) {
        return result.put(Conventions.getVariableName(value), value);
    }
    
}
