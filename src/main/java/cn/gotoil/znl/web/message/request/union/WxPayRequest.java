package cn.gotoil.znl.web.message.request.union;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/17.9:06
 */
@Setter
@Getter
@JsonSerialize
public class WxPayRequest  {

    @NotNull(message = "支付金额不能为空")
    @Min(value = 1,message = "支付金额最小值1")
    @Max(value = 999999999999999L,message = "支付金额在1-15位数字")
    private long trxamt=1;

    @NotNull(message="支付方式不能为空")
    @Size(max = 3,message = "支付方式最大长度不能超过3位")
    private String payType="W02";

    private String reqsn = String.valueOf(System.currentTimeMillis());

    @Size(max = 100,message = "订单商品名称长度不能超过100位")
    private String body ="国通石油" ;

    @Size(max =50 ,message = "备注信息长度不能超过50位")
    private String remark ="";


    private String limitPay = "no_credit"   ;
@Max(99)
    private int validtime =15;

    private String notifyUrl ="";

//    @NotNull
    private String cusid;
//@NotNull
    private String appid;

    private String appKey;

    private String wxAppid;

    private String wxAppKey;
}
