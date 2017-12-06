
package com.example.ms_demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ms_demo.alipay.AlipayUtil;
import com.example.ms_demo.alipay.config.AlipayConfig;
import com.example.ms_demo.alipay.util.AlipaySubmit;

/**
 * 支付宝支付和回调接口
 *
 * @author cjw
 */
@RequestMapping("alipay")
@Controller
public class AlipayController {
    private static Logger log = LoggerFactory.getLogger(AlipayController.class);
    
    /**
     * ----------------------------------支付宝支付-------------------------------
     **/
    
    @GetMapping("payFormSubmit")
    public String payFormSubmit(HttpServletRequest request, Model module) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int random = (int) ((Math.random() * 9 + 1) * 10000);
        // 更新订单号
        String orderNo = dateTime + random;
        String msg__ = "测试充值";
        //////////////////////////////////// 请求参数//////////////////////////////////////
        
        // 支付类型
        String payment_type = "1";
        // 必填，不能修改
        // 服务器异步通知页面路径
        String notify_url = "www.baidu.com";
        // 需http://格式的完整路径，不能加?id=123这类自定义参数
        // 页面跳转同步通知页面路径
        String return_url = "www.baidu.com";
        // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
        // 卖家支付宝帐户
        String seller_email = "dingr@51box.cn";
        // 必填
        // 商户订单号
        // String out_trade_no = orderNo;
        // 商户网站订单系统中唯一订单号，必填
        // 订单名称
        
        String subject = msg__;
        // 必填
        // 付款金额
        String total_fee = 0.01 + "";
        // 必填
        // 订单描述
        String body = msg__;
        // 商品展示地址
        String show_url = "www.baidu.com";
        // 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
        // 防钓鱼时间戳
        String anti_phishing_key = "";
        try {
            anti_phishing_key = AlipaySubmit.query_timestamp();
            log.error("anti_phishing_key", anti_phishing_key);
        } catch (Exception e) {
            log.error("", e);
        }
        // 若要使用请调用类文件submit中的query_timestamp函数
        // 客户端的IP地址
        String exter_invoke_ip = request.getRemoteAddr();
        // 非局域网的外网IP地址，如：221.0.0.1
        
        // 订单类型
        
        //////////////////////////////////////////////////////////////////////////////////
        
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", orderNo);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        sParaTemp.put("extra_common_param", "12");
        // 建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认");
        module.addAttribute("htmls", sHtmlText);
        return "alipay";
    }
    
    /**
     * 增值服务异步回调通知 方法描述
     */
    @GetMapping("alipay_callback")
    public String alipay_callback(HttpServletRequest request) {
        
        return "";
    }
    
    @PostMapping("alipay_callback_notify")
    public void alipay_callback_notify(HttpServletRequest request) {
        log.error("[支付宝异步通知返回付款");
        long timestamp = new Date().getTime();
        Map<String, String> params = new HashMap<>();
        int result = 0;
        try {
            log.error("[支付宝异步通知返回付款]ip " + request.getRemoteAddr() + " ||" + request.getLocalAddr() + "  " + timestamp);
            params = AlipayUtil.getParams(request);
            log.error("返回参数信息:" + params);
            if ("TRADE_FINISHED".equals(params.get("trade_status"))) {
                result = 1;
            } else if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
            } else {
                log.error("状态不正确：" + params.get("trade_status"));
            }
        } catch (Exception e) {
            log.error("[支付宝异步通知返回付款 处理失败]error 出现异常：" + e);
        }
        if (result != 1) {
            log.error("[支付宝异步通知返回付款 处理失败]" + result);
        }
        
    }
    
}
