package com.project.demo.model;

public class AliPayBena {

    private String out_trade_no;//商户订单号
    private double total_amount;//订单总金额
    private String subject;//订单标题
    private String body;

    @Override
    public String toString() {
        return "AliPayBena{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", total_amount=" + total_amount +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
