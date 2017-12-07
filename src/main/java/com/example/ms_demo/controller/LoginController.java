package com.example.ms_demo.controller;

import java.io.BufferedOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;

@Controller
public class LoginController {
    
    // @Autowired
    // private GiftInfoMapper giftInfoMapper;
    
    @GetMapping("login")
    public String login() {
        return "login";
    }
    
    @GetMapping("index")
    public String index(HttpServletRequest request) {
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String headerName = e.nextElement();// 透明称
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                System.out.println(headerName + ":" + headerValues.nextElement());
            }
        }
        return "index";
    }
    
    @GetMapping("index1")
    public @ResponseBody Map<String, Object> index1() {
        // UserLogin u=this.getManagerUserDetails();
        // giftInfoMapper.selectByExample(new GiftInfoCriteria());
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(1, 10);
        // List<GiftInfo> list=giftInfoMapper.selectByExample(null);
        // map.put("list", list);
        map.put("u", "");
        return map;
    }
    
    @GetMapping("index2")
    public String index2(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.addHeader("Content-disposition", "attachment;filename=" + "yyyy-MM-dd" + ".xlsx");
        BufferedOutputStream bos = null;
        ServletOutputStream out = null;
        
        // SXSSFWorkbook wb = null;
        // AttendRecordDetail attendRecordDetail = null;
        String status = null;
        return "index";
    }
    
}
