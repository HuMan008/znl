package com.znl.web.message.response.union;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.12:24
 * 通联用户注册响应
 */
public class UnionRegisterResponse {

    private String merchantId ;// 商户号 30 必填 数字串，与提交时的商户号保持一致

    private String signType;// 签名类型 2 必填 固定选择值：0，MD5签名
    private String userId;// 通联用户编号 50 必填 数字串
    private String  resultCode ;//结果代码 10 必填 返回处理结果代码
    private String returnDatetime ;//    结果返回时间 14 必填 系统返回结果的时间，日期格式： yyyyMMDDhhmmss
    private String  signMsg ;//签名字符串 1024 必填 以上所有非空参数按上述顺序组合并经商户MD5签 名后生成该值。


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnDatetime() {
        return returnDatetime;
    }

    public void setReturnDatetime(String returnDatetime) {
        this.returnDatetime = returnDatetime;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    @Override
    public String toString() {
        return "UnionRegisterResponse{" +
                "merchantId='" + merchantId + '\'' +
                ", signType='" + signType + '\'' +
                ", userId='" + userId + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", returnDatetime='" + returnDatetime + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }


}
