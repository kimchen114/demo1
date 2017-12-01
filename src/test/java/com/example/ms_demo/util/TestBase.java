package com.example.ms_demo.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ActiveProfiles("dev")
//@SpringApplicationConfiguration(classes=Application.class)
public class TestBase {

	@Autowired
	public WebApplicationContext wac;
	
	public MockMvc mockMvc;
	
	public Map<String,String> params=new HashMap<>();
	
	public String urlclass="";  //对应的类上面路径
	public String urlmethod=""; //对应的方法上面路径
	
	@Before
	public void setup(){
		this.mockMvc=MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	public void getMethod() throws Exception{
		this.mockMvc.perform(
				get(urlclass+"/"+urlmethod)
				.param("data", hashMapToJson(params))).andDo(print())
		.andReturn().getResponse().getContentAsString();
	}
	
	
	public void postMethod(String obj) throws Exception{
		this.mockMvc.perform(
				post(urlclass+"/"+urlmethod+"?")
				.param("data", hashMapToJson(params))
				.contentType(MediaType.APPLICATION_JSON).content(obj)
				).andDo(print())
		.andReturn().getResponse().getContentAsString();
	}
	
	
	public void setMethod(String obj) throws Exception{
		this.mockMvc.perform(
				put(urlclass+"/"+urlmethod+"?")
				.param("data", hashMapToJson(params))
				.contentType(MediaType.APPLICATION_JSON).content(obj)
				).andDo(print())
		.andReturn().getResponse().getContentAsString();
	}
	
	
	public void setMethod() throws Exception{
		this.mockMvc.perform(
				put(urlclass+"/"+urlmethod+"?")
				.param("data", hashMapToJson(params))
				).andDo(print())
		.andReturn().getResponse().getContentAsString();
	}
	
	
	public void deleteMethod() throws Exception{
		this.mockMvc.perform(delete(urlclass+"/"+urlmethod+"?").param("data", hashMapToJson(params))).andDo(print())
		.andReturn().getResponse().getContentAsString();
	}
	
	
	private String hashMapToJson(Map<String, String> params) {
		StringBuffer sb=new StringBuffer("{");
		for (Map.Entry<String, String> map : params.entrySet()) {
			sb.append("'"+map.getKey()+"':");
			sb.append("'"+map.getValue()+"',");
		}
		String json=sb.toString();
		json=json.substring(0, json.lastIndexOf(","))+"}";
		return json;
	}
	
	public void printJson(String ison){
		System.out.println();
		
	}
	
	
}
