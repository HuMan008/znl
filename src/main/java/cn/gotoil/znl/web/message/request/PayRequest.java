package cn.gotoil.znl.web.message.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wh on 2017/5/3.
 */
@Getter
@Setter
public class PayRequest {

    /***
     * 应用类别
     * **/
    @NotNull(message = "应用类别不得为空!")
    private  String  appID;

    /***
     * 支付方式
     * **/
    @NotNull(message = "支付方式不得为空!")
    private  String  payType;


    private  String order_id_actual;

    @Size(max=1000,message = "额外参数长度不得大于1000！")
    private  String extra_param;

    @Size(max=300)
    private  String return_url_actual;

    @Size(max=300)
    private  String notify_url_actual;


    /**订单 有效 时间(分钟)**/
    @Min(value = 0 ,message = "有效时间不得为负！")
    private  int timeout_minute;


    //交易标题
    @Size(max = 50,message = "交易标题的长度不得大于50！")
    private  String  subject;


    //请求支付的金额  分
    @NotNull(message = "支付金额不得为空！")
    @Min(value = 1,message = "支付金额不得少于1分！")
    private  Integer  amount;


    public PayRequest() {
    }
}
