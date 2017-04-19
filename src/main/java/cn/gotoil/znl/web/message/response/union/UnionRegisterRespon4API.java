package cn.gotoil.znl.web.message.response.union;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/18.10:16
 */
public class UnionRegisterRespon4API {
    private String userId;// 通联用户编号 50 必填 数字串
    private String  resultCode ;//结果代码 10 必填 返回处理结果代码
   private String partnerUserId;

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

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }
}
