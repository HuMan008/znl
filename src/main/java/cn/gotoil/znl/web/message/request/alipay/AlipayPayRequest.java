package cn.gotoil.znl.web.message.request.alipay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * Created by wh on 2017/4/18.
 */
@Getter
@Setter
@ToString
public class AlipayPayRequest {


    /***
     *  商户订单号，商户网站订单系统中唯一订单号
     * **/
    @NotNull(message = "商户订单号不得为空！")
    private String out_trade_no;

    /***
     *  订单名称
     * **/
    @NotNull(message = "订单名称不得为空！")
    private String subject;

    /***
     *   付款金额
     * **/
    @NotNull(message = "付款金额不得为空！")
    private Double total_amount ;

    /***
     *  商品描述
     * **/
    private String desc;

    /***
     *  销售产品码
     * **/
    @NotNull(message = "销售产品码不得为空！")
    private String product_code;

    /***
     *  超时时间
     * **/
    private String timeout_express;


    public AlipayPayRequest() {
        this.timeout_express = "2m";
    }


    public AlipayPayRequest(String out_trade_no, String subject, Double total_amount, String desc, String product_code, String timeout_express) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_amount = total_amount;
        this.desc = desc;
        this.product_code = product_code;
        this.timeout_express = timeout_express;
    }
}
