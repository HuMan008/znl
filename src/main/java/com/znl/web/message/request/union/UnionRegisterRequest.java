package com.znl.web.message.request.union;

import com.znl.common.tools.encoder.Hash;
import com.znl.config.property.SybConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.11:39
 * 通联用户注册请求
 */
public class UnionRegisterRequest {

    //    0表示商户使用MD5对信息进行签名 1表示商户使用证书方式对信息进行签名，使用证 书的话，商户必须自己生产证书，并将证书通过商 户服务平台将公钥上传至通联的系统
    @NotNull
    private String signType = "0";

    @NotNull
    @Size(max = 30)
    private String merchantId = SybConstants.GateWayConsts.MERCHANTID;
    @NotNull
    @Size(max = 32)
    private String partnerUserId;

    @NotNull
    @Size(max = 1024)
    private String signMsg;


    public String getSignType() {
        return signType.trim();
    }

    public void setSignType(String signType) {
        this.signType = signType.trim();
    }

    public String getMerchantId() {
        return merchantId.trim();
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId.trim();
    }

    public String getPartnerUserId() {
        return partnerUserId.trim();
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId.trim();
    }

    public String getSignMsg() {
        return signMsg.trim();
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg.trim();
    }


    public void doSign() {
        setMerchantId(SybConstants.GateWayConsts.MERCHANTID);
        String signMsg = "&signType=" + getSignType() + "&merchantId=" + getMerchantId()
                + "&partnerUserId=" + getPartnerUserId() + "&key=" + SybConstants.GateWayConsts.MERCHANTKEY + "&";
        setSignMsg(Hash.md5(signMsg).toUpperCase());
    }


    @Override
    public String toString() {
        return "UnionRegisterRequest{" +
                "signType='" + signType + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", partnerUserId='" + partnerUserId + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }
}
