package com.example.ms_demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.SysUser;
import com.example.ms_demo.domain.SysUserCriteria;
import com.example.ms_demo.mapper.SysUserMapper;
import com.example.ms_demo.util.Collections3;

@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	
	
	public SysUser getByUsername(String username){
		SysUserCriteria example = new SysUserCriteria();
		example.createCriteria().andUsernameEqualTo(username).andStatusEqualTo(0);
		List<SysUser> list=sysUserMapper.selectByExample(example);
		if (Collections3.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	public SysUser getById(Integer userId){
		return sysUserMapper.selectByPrimaryKey(userId);
	}
	
	
	public int update(SysUser sysUser){
		return sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}
	
	
	
	
	
}
