package com.example.ms_demo.user;

public interface CareEnum {


   
    
    
     public enum aa{
        ww("1","2");
       private String key;
       private String value;
       
       private aa( String key, String value) {
           this.key = key;
           this.value = value;
       }

        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        
    }
     public enum aa1{
    	 ww("1","2");
    	 private String key;
    	 private String value;
    	 
    	 private aa1( String key, String value) {
    		 this.key = key;
    		 this.value = value;
    	 }
    	 
    	 public String getKey() {
    		 return key;
    	 }
    	 
    	 public void setKey(String key) {
    		 this.key = key;
    	 }
    	 
    	 public String getValue() {
    		 return value;
    	 }
    	 
    	 public void setValue(String value) {
    		 this.value = value;
    	 }
    	 
     }
    
}














