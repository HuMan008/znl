package com.znl.web.message.response.union;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/10.8:19
 * 支付结果响应
 */
public class PayResultResponse {

private String merchantId ; //商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String  version; //网关返回支付结 果接口版本 10 不可空 固定选择值：v1.0；注意为小写字母
    private String language ;// 网页显示语言种类  2 可空 固定值：1；1 代表中文显示
    private String signType ;//签名类型 2 不可空 固定选择值：0、1，与交订单时的签名类型保持一 致 payType 支付方式 2 可空 字符串，与交订单时的支付方式一致
    private String issuerId ;// 发卡方机构代码 8 可空 固定为空
    private String paymentOrderId ;// 通联订单号 50 不可空 字符串，通联订单号
    private String orderNo ; //商户订单号 50 不可空 字符串，与交订单时的商户订单号保持一致
    private String orderDatetime;// 商户订单交时间14 不可空 数字串，与交订单时的商户订单交时间保持一致
    private long orderAmount ;//商户订单金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10 元交时金额应为 1000如果是美元，单位是美分，即 10 美元交时金额为1000
    private String  payDatetime;// 支付完成时间 14 不可空 日期格式：yyyyMMDDhhmmss，例如： 20121116020101
    private long  payAmount ;// 订单实际支付金 额 10 不可空 整型数字，实际支付金额，用户实际支付币种为人民 币； 以分为单位，例如 10 元返回时应为 1000 分
    private String  ext1 ;//扩展字段 1 128 可空 字符串，与交订单时的扩展字段 1 保持一致
    private String ext2 ;// 扩展字段 2 128 可空 字符串，与交订单时的扩展字段 2 保持一致
    private String payResult;// 处理结果 2 不可空 1：支付成功 仅在支付成功时通知商户。 商户可以通过查询接口查询订单状态。 errorCode 错误代码 10 可空 固定为空
    private String returnDatetime ;//结果返回时间 14 不可空 系统返回支付结果的时间，日期格式： yyyyMMDDhhmmss signMsg 签名字符串 1024 不可空 以上所有非空参数按上述顺序与密钥组合，经加密后 生成该值。


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getReturnDatetime() {
        return returnDatetime;
    }

    public void setReturnDatetime(String returnDatetime) {
        this.returnDatetime = returnDatetime;
    }

    @Override
    public String toString() {
        return "PayResultResponse{" +
                "merchantId='" + merchantId + '\'' +
                ", version='" + version + '\'' +
                ", language='" + language + '\'' +
                ", signType='" + signType + '\'' +
                ", issuerId='" + issuerId + '\'' +
                ", paymentOrderId='" + paymentOrderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderDatetime='" + orderDatetime + '\'' +
                ", orderAmount=" + orderAmount +
                ", payDatetime='" + payDatetime + '\'' +
                ", payAmount=" + payAmount +
                ", ext1='" + ext1 + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", payResult='" + payResult + '\'' +
                ", returnDatetime='" + returnDatetime + '\'' +
                '}';
    }

    public void parseRequest(HttpServletRequest request) {



    }
}
