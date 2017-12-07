package com.example.ms_demo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestUser {
    
    private Integer sex;
    private String sexview;
    
    
    public void setSex(Integer sex) {
        this.sex = sex;
        setSexview(CareEnum.SexEnum.getValueByKey(sex));
    }
    
    
}
