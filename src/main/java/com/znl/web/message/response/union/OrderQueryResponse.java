package com.znl.web.message.response.union;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.8:53
 * 订单查询结果响应
 */
public class OrderQueryResponse {

     private String payDatetime;
     private String userName;
    private String credentialsType;
    private String pan;
    private String txOrgId;
    private String ext1;
    private long payAmount;
    private String returnDatetime;
    private String credentialsNo;
    private String issuerId;
    private String signMsg  ;
    private String payType ;
    private String language;
    private String errorCode;
    private String merchantId;
    private String orderDatetime  ;
    private String version;
    private String orderNo;
    private String ext2;
    private String signType;
    private long orderAmount;
    private String extTL;
    private String paymentOrderId;
    private String payResult;

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTxOrgId() {
        return txOrgId;
    }

    public void setTxOrgId(String txOrgId) {
        this.txOrgId = txOrgId;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }

    public String getReturnDatetime() {
        return returnDatetime;
    }

    public void setReturnDatetime(String returnDatetime) {
        this.returnDatetime = returnDatetime;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getExtTL() {
        return extTL;
    }

    public void setExtTL(String extTL) {
        this.extTL = extTL;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    @Override
    public String toString() {
        return "OrderQueryResponse{" +
                "payDatetime='" + payDatetime + '\'' +
                ", userName='" + userName + '\'' +
                ", credentialsType='" + credentialsType + '\'' +
                ", pan='" + pan + '\'' +
                ", txOrgId='" + txOrgId + '\'' +
                ", ext1='" + ext1 + '\'' +
                ", payAmount=" + payAmount +
                ", returnDatetime='" + returnDatetime + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", issuerId='" + issuerId + '\'' +
                ", signMsg='" + signMsg + '\'' +
                ", payType='" + payType + '\'' +
                ", language='" + language + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", orderDatetime='" + orderDatetime + '\'' +
                ", version='" + version + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", signType='" + signType + '\'' +
                ", orderAmount=" + orderAmount +
                ", extTL='" + extTL + '\'' +
                ", paymentOrderId='" + paymentOrderId + '\'' +
                ", payResult='" + payResult + '\'' +
                '}';
    }
}
