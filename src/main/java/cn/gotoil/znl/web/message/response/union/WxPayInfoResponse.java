package cn.gotoil.znl.web.message.response.union;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/13.8:28
 */
public class WxPayInfoResponse {
    private String appId;
    private  String timeStamp;
    private String signType;
    @JSONField(name="package")
    private String aPackage  ;
    private String  nonceStr;
    private String paySign ;

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getaPackage() {
        return aPackage;
    }

    public void setaPackage(String aPackage) {
        this.aPackage = aPackage;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


}
