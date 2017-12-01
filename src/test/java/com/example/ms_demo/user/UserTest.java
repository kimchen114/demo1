package com.example.ms_demo.user;

import org.junit.Before;
import org.junit.Test;

import com.example.ms_demo.util.TestBase;


public class UserTest extends TestBase {


	@Before
	public void before1(){
		super.urlclass="/v1/user";
		
		
	}
	
	
	@Test
	public void getTestBase() throws Exception{
		super.urlmethod="getUser";
		super.params.put("userId", "5");
		this.getMethod();
		
	}

	
	
	
	
	
	
	
	
	
	
}
