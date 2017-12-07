package com.example.ms_demo.user;

public interface CareEnum {


   
    
     public enum SexEnum{
        MAN(1,"男"),
        BANNANBANNV(3,"半男半女"),
        FEMALE(2,"女");
       private Integer key;
       private String value;
       
       private SexEnum( Integer key, String value) {
           this.key = key;
           this.value = value;
       }

        public Integer getKey() {
            return key;
        }
        
        public void setKey(Integer key) {
            this.key = key;
        }
        
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        public static String getValueByKey(Integer key) {
            SexEnum[] sexviews=  CareEnum.SexEnum.values();
            for (SexEnum sexview : sexviews) {
                if (key==sexview.getKey()) {
                    return sexview.getValue();
                }
            }
            return null;
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














