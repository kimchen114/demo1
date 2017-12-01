package com.example.ms_demo.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.User;
import com.example.ms_demo.domain.UserCriteria;
import com.example.ms_demo.domain.UserCriteria.Criteria;
import com.example.ms_demo.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	
	
	public List<User> getList(String username,String startTime,String endTime){
		UserCriteria example = new UserCriteria();
		example.setOrderByClause("id desc");
		Criteria criteria =example.createCriteria().andIsDeletedEqualTo(0);
		if(StringUtils.isNotBlank(username)) {
			criteria.andUsernameLike("%"+username+"%");
		}
		if(StringUtils.isNotBlank(startTime)) {
			
			try {
				criteria.andGmtCreateGreaterThanOrEqualTo(DateUtils.parseDate(startTime,"yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		if(StringUtils.isNotBlank(endTime)) {
			try {
				endTime=LocalDate.parse(endTime).plusDays(1).toString();
				criteria.andGmtCreateLessThanOrEqualTo(DateUtils.parseDate(endTime,"yyyy-MM-dd"));
			} catch (Exception e) {
			}
		}
		
		List<User> list=userMapper.selectByExample(example);
		return list;
	}
	
	public User getById(Integer userId){
		return userMapper.selectByPrimaryKey(userId);
	}
	
	
	public int deleteByPrimaryKey(Integer userId){
		return userMapper.deleteByPrimaryKey(userId);
	}
	
	public int updateByPrimaryKeySelective(User user){
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	public int save(User user){
		return userMapper.insertSelective(user);
	}
	
}
