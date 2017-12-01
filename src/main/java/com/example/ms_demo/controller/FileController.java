package com.example.ms_demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ms_demo.domain.UserFile;
import com.example.ms_demo.service.FileService;

@Controller
@RequestMapping("/file")
public class FileController {
	
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("upload")
	public @ResponseBody Map<String,Object> upload(MultipartFile mfile) throws IOException {
		
		String fileName = mfile.getOriginalFilename();
		String fileExtension = FilenameUtils.getExtension(fileName);
		Long size = mfile.getSize();
		String path = "D://app/upload/";
		System.out.println(fileName);
		LocalDate now =  LocalDate.now();
		path=path+now.getYear()+java.io.File.separator+now.getMonthValue()+java.io.File.separator+now.getDayOfMonth();
		java.io.File nfile=new java.io.File(path);
		if(!nfile.exists()) {
			nfile.mkdirs();
		}
		path=path+java.io.File.separator+fileName;
		java.io.File wfile=new java.io.File(path);
		FileUtils.copyInputStreamToFile(mfile.getInputStream(), wfile);
		
		UserFile userFile = new UserFile();
		userFile.setName(fileName);
		userFile.setSize(size.toString());
		userFile.setUrl(path);
		userFile.setGmtCreate(new Date());
		userFile.setType(fileExtension);
		fileService.save(userFile);
		return new HashMap<String,Object>();
	}
	
	
	
	@GetMapping
	public String tofile() {
		
		return "file";
	}
	
	@GetMapping("download")
	public void download(Long fileId,HttpServletResponse response) throws IOException {
		
		UserFile userFile = fileService.getById(fileId);
		
        response.addHeader("Content-Length", userFile.getSize());

        String filename = userFile.getName();
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        
        OutputStream op = response.getOutputStream();
        response.setContentType("application/octet-stream;charset=UTF-8");
        
        FileUtils.copyFile(new File(userFile.getUrl()), op);
		
	}
	
	
}
