package cn.gotoil.znl.web.message.response.alipay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by wh on 2017/5/15.
 */
@Getter
@Setter
@ToString
public class WapPayResponse {


    /**销售产品码**/
    private String  product_code;

    /**商户订单号**/
    private String  out_trade_no;

    /**订单名称**/
    private String  subject;

    /**付款金额**/
    private Double  total_amount;

    /**商品描述**/
    private String  desc;

    /**订单虚拟ID【即：在虚拟网管中的id】**/
    private String  orderVirtualID;


    public WapPayResponse() {
    }
}
