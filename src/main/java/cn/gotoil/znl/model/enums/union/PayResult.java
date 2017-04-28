package cn.gotoil.znl.model.enums.union;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/27.9:06
 */
@Getter
@ToString
@AllArgsConstructor
public enum PayResult {
    allInPay_PaySuccess("1","支付成功") ,
    allInPay_PayWait("0","未支付") ,


    wx_PaySuccess("0000","交易成功"),
   wx_PayTimeout("3045","交易超时"),
   wx_MoneyNotEnought("3008","余额不足"),
   wx_PayFail("3999","交易失败"),


    ;
    private String code;
    private String desc;
}
