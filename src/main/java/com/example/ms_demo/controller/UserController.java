package com.example.ms_demo.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ms_demo.domain.User;
import com.example.ms_demo.service.UserService;
import com.example.ms_demo.util.ExcelWriter;
import com.example.ms_demo.util.RestResult;
import com.example.ms_demo.util.XwpfTUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService service;
    
    @GetMapping("userIndex")
    public String userIndex() {
        return "user_list";
    }
    
    @GetMapping("edit")
    public String edit(Model module, Integer userId) {
        return "user_edit";
    }
    
    @GetMapping("userInfo")
    public String userInfo(Model module, Integer userId) {
        return "user_info";
    }
    
    @GetMapping("add")
    public @ResponseBody Map<String, Object> addUser() {
        Map<String, Object> result = new HashMap<String, Object>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setAge(i + 1);
            user.setPhone(13908277 + i + "");
            user.setUsername("姓名" + (i + 1));
            user.setGmtCreate(new Date());
            user.setGmtModified(new Date());
            service.save(user);
        }
        result.put("user", "");
        return result;
    }
    
    @PostMapping("delete")
    @ResponseBody
    public RestResult delete(@RequestBody Integer userId) {
        if (userId == null) {
            return RestResult.failed("userId不能为空");
        }
        User user = service.getById(userId);
        if (user == null) {
            return RestResult.failed("用户不存在");
        }
        user.setIsDeleted(1);
        service.updateByPrimaryKeySelective(user);
        return RestResult.succsee();
    }
    
    @PostMapping(value="update")
    @PreAuthorize("hasRole('admin_user')")
    @ResponseBody
    public RestResult update( User user) {
        user.setGmtModified(new Date());
        service.updateByPrimaryKeySelective(user);
        return RestResult.succsee();
    }
    
    @PostMapping("add")
    @ResponseBody
    public RestResult add(@RequestBody User user) {
        user.setGmtModified(new Date());
        user.setGmtCreate(new Date());
        service.save(user);
        return RestResult.succsee();
    }
    
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer size, String username, String startTime, String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.startPage(page, size);
        Page<User> list = (Page<User>) service.getList(username, startTime, endTime);
        result.put("list", list);
        result.put("currPage", page);
        result.put("totalPage", list.getPages());
        result.put("totalCount", list.getTotal());
        return result;
    }
    
    @GetMapping("getById/{userId}")
    @ResponseBody
    public Map<String, Object> getById(@PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = service.getById(userId);
        result.put("user", user);
        return result;
    }
    
    @GetMapping("export")
    public void export(HttpServletResponse response) {
        OutputStream out = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=loanple.xls");
            out = new BufferedOutputStream(response.getOutputStream());
            ExcelWriter excelWriter = new ExcelWriter(out);
            
            HSSFRow row = excelWriter.sheet.createRow(0);
            // 设置列宽。第一个参数代表列id(从0开始),第2个参数代表宽度值 参考 ："2012-08-10"的宽度为2500
            
            String[] tital = { "放款日期", "业务员", "客户姓名", "保证金", "一抵金额", "二抵金额", "合同期限", "还息日", "金额", "保险费", "征信费", "上门费",
                    "一抵利息标准", "二抵利息标准", "服务费标准", "一抵服务费", "二抵服务费" };
            for (int i = 0; i < tital.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(tital[i]);
                excelWriter.sheet.setColumnWidth(i, 3800);
                cell.setCellStyle(excelWriter.setCellStyle());
                row.setHeight((short) 600);
            }
            
            excelWriter.export();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 导出word合同(抵押)
     */
    @RequestMapping(value = { "/exportword" }, method = RequestMethod.GET)
    public void exportApplyForm(String id, HttpServletResponse response) throws Exception {
        
        SimpleDateFormat fomt2 = new SimpleDateFormat("yyyy-MM-dd");
        
        XwpfTUtil xwpfTUtil = new XwpfTUtil();
        User user = new User();
        XWPFDocument doc;
        String fileNameInResource = "static/word/lendApplyTable6.docx";
        InputStream is;
        /* is = new FileInputStream(filePath); */
        is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);
        doc = new XWPFDocument(is);
        
        service.exportContractInfoWord(doc, user, xwpfTUtil);
        
        OutputStream os = response.getOutputStream();
        
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=123.docx");
        
        doc.write(os);
        
        xwpfTUtil.close(os);
        xwpfTUtil.close(is);
        
        os.flush();
        os.close();
    }
    
}
