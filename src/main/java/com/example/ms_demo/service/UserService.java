package com.example.ms_demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.User;
import com.example.ms_demo.domain.UserCriteria;
import com.example.ms_demo.domain.UserCriteria.Criteria;
import com.example.ms_demo.mapper.UserMapper;
import com.example.ms_demo.util.DateUtil;
import com.example.ms_demo.util.XwpfTUtil;

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
				criteria.andGmtCreateGreaterThanOrEqualTo(DateUtil.parse(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
				criteria.andGmtCreateLessThanOrEqualTo(DateUtil.parse(endTime+" 23:59"));
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

	public void exportContractInfoWord(XWPFDocument doc, User user, XwpfTUtil xwpfTUtil) {

		
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("${userName}", "123");

		xwpfTUtil.replaceInPara(doc, params);
		// 替换表格里面的变量
		xwpfTUtil.replaceInTable(doc, params);
		
	}

    public int batchAdd(List<User> list) {
        
        return userMapper.batchAdd(list);
    }
	
}
