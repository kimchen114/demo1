package com.example.ms_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_demo.domain.UserFile;
import com.example.ms_demo.mapper.UserFileMapper;

@Service
public class FileService {

	@Autowired
	private UserFileMapper fileMapper;
	
	
	public int save(UserFile record) {
		return fileMapper.insertSelective(record);
	}
	
	public UserFile getById(Long id) {
		return fileMapper.selectByPrimaryKey(id);
	}
	
	
	
}
