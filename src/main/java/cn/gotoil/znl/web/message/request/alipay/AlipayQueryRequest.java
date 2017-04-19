package cn.gotoil.znl.web.message.request.alipay;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wh on 2017/4/18.
 */
@Getter
@Setter
public class AlipayQueryRequest {

    /***
     *  商户订单号
     * **/
    private String out_trade_no;

    /***
     *  支付宝交易号
     * **/
    private String trade_no;

}
