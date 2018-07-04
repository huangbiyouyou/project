package com.project.demo.controller;

import com.project.demo.Respone.RespResult;
import com.project.demo.constant.ProjectConstant;
import com.project.demo.model.WxOrder;
import com.project.demo.utils.TimeUtil;
import com.project.demo.utils.WxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class WxPayController extends BaseController{


    @RequestMapping("/wxpay")
    @ResponseBody
    public RespResult loginAction(HttpServletResponse response, HttpServletRequest request,
                                  @RequestParam("body") String body,
                                  @RequestParam("outTradeNo") String outTradeNo,
                                  @RequestParam("productId") String productId,
                                  @RequestParam("createIp") String createIp,
                                  @RequestParam("totalFee") String totalFee) throws Exception{

        response.setHeader("Access-Control-Allow-Origin", "*");
        RespResult resp = new RespResult();

        HashMap<String, String> map = new HashMap<>();

        String nonceStr= UUID.randomUUID().toString().replaceAll("-","").substring(0, 32);
        String startTime = TimeUtil.getTime("yyyyMMddHHmmss");
        map.put("appid", ProjectConstant.APPID);
        map.put("body",body);//商品描述
        map.put("mch_id",ProjectConstant.MCHID);
        map.put("nonce_str",nonceStr);
        map.put("time_start", startTime);
        map.put("fee_type", "CNY");
        map.put("notify_url","http://station.51educity.com/result.action");
        map.put("out_trade_no",outTradeNo);//商户订单号
        map.put("product_id",productId);//商品ID
        map.put("spbill_create_ip",createIp);
        map.put("total_fee",totalFee);
        map.put("trade_type","NATIVE");

        String xml = WxUtils.generateSignedXml(map,ProjectConstant.APIKEY, ProjectConstant.SignType.MD5);

        String result = WxUtils.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", xml);
        String codeUrl="";
        WxOrder wxOrder=null;
        Map<String, String> resultMap = WxUtils.xmlToMap(result);
            String returnCode = resultMap.get("return_code");
            if (returnCode.equals("SUCCESS")) {
                String resultCode = resultMap.get("result_code");
                if (resultCode.equals("SUCCESS")){
                     wxOrder=new WxOrder();
                    String tradeType = resultMap.get("trade_type");
                    String prepayId = resultMap.get("prepay_id");
                    codeUrl = resultMap.get("code_url");

                    wxOrder.setBody(body);
                    wxOrder.setOutTradeNo(outTradeNo);
                    wxOrder.setProductId(productId);
                    wxOrder.setCreateIp(createIp);
                    wxOrder.setTotalFee(String.valueOf(Integer.parseInt(totalFee)/100));
                    wxOrder.setTimeStart(startTime);
                    wxOrder.setTradeType(tradeType);
                    wxOrder.setPrepayId(prepayId);
                    wxOrder.setCodeUrl(codeUrl);
                    wxOrder.setOrderStatus(1);//待支付，未成功
                   // wxOrderDao.save(wxOrder);
                }else {
                    resp.setCode(-1);
                    resp.setMsg(resultMap.get("err_code_des"));
                    logger.info("[actor]err_code:{},err_code_des:{},outTradeNo:{}",resultMap.get("err_code"),resultMap.get("err_code_des"),outTradeNo);
                    return resp;
                }

            }else {
                resp.setCode(-1);
                resp.setMsg("return_msg");
                logger.info("[actor]FAIL body:{},outTradeNo:{}",resultMap.get("return_msg"),outTradeNo);
                return resp;
            }
        resp.setCode(0);
        resp.setData(codeUrl);
        resp.setMsg("成功");
        return resp;

    }

    @RequestMapping("/result.action")
    public void payAction(HttpServletResponse response, HttpServletRequest request)throws Exception {
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //判断签名是否正确
        if(WxUtils.isSignatureValid(sb.toString(),ProjectConstant.APIKEY)) {
            Map<String, String> map = WxUtils.xmlToMap(sb.toString());
            String resXml = "";
            if("SUCCESS".equals(map.get("return_code"))){
                if ("SUCCESS".equals(map.get("return_code"))) {

                    String out_trade_no = map.get("out_trade_no");
                    Integer total_fee = Integer.parseInt(map.get("total_fee"));
                    String transaction_id = map.get("transaction_id");
                    String time_end = map.get("time_end");

//                    WxOrder wxOrder = wxOrderDao.findByOutTradeNo(out_trade_no);
//                    if (wxOrder.getOrderStatus()!=0) {
//                        if (wxOrder.getTotalFee().equals(String.valueOf(total_fee / 100))) {
//
//                            wxOrder.setTransactionId(transaction_id);
//                            wxOrder.setTimeEnd(time_end);
//                            wxOrder.setOrderStatus(0);
//                            wxOrderDao.save(wxOrder);
//                            logger.info("[WxPayController]支付成功 out_trade_no:{}", out_trade_no);
//
//                            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
//                                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
//                        } else {
//                            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                                    + "<return_msg><![CDATA[金额不对]]></return_msg>" + "</xml> ";
//                        }
//                    }else {
//                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                                + "<return_msg><![CDATA[订单已支付]]></return_msg>" + "</xml> ";
//                    }
                }else {
                    logger.info("[WxPayController]支付失败,错误信息 err_code:{},err_code_des:{}",map.get("err_code"),map.get("err_code_des"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }

            } else {
                logger.info("支付失败,错误信息：" + map.get("return_msg"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }
}
