package com.project.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.project.demo.Respone.RespResult;
import com.project.demo.model.AliPayBena;
import com.project.demo.utils.AliPayUtils;
import com.project.demo.utils.AlipayHashMap;
import com.project.demo.utils.TimeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AliPayController extends BaseController{

    @RequestMapping("/alipay")
    public RespResult loginAction(HttpServletResponse response, HttpServletRequest request)  {

        response.setHeader("Access-Control-Allow-Origin", "*");
        RespResult resp = new RespResult();

        AlipayHashMap alipayHashMap=new AlipayHashMap();

        AliPayBena aliPayBena=new AliPayBena();

        String serverUrl="https://openapi.alipay.com/gateway.do";

        aliPayBena.setBody("QQ充值");
        aliPayBena.setOut_trade_no("1333552432425");
        aliPayBena.setSubject("Iphone6 16G");
        aliPayBena.setTotal_amount(25);

        String startTime = TimeUtil.getTime("yyyy-MM-ddHH:mm:ss");
        alipayHashMap.put("app_id","");
        alipayHashMap.put("method","alipay.trade.precreate");
        alipayHashMap.put("charset","utf-8");
        alipayHashMap.put("sign_type","RSA2");
        alipayHashMap.put("sign","");
        alipayHashMap.put("timestamp",startTime);
        alipayHashMap.put("version","1.0");
        alipayHashMap.put("biz_content", net.sf.json.JSONObject.fromObject(aliPayBena));

        logger.info("sssssss"+ AliPayUtils.getSignContent(alipayHashMap));

        //AlipayClient alipayClient=new DefaultAlipayClient(serverUrl,)

        return resp;

    }

}
