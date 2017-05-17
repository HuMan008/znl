package cn.gotoil.znl.web.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wh on 2017/5/17.
 * 订单支付状态 主动查询
 */
@Getter
@Setter
public class QueryRequest {

    /**订单虚拟ID**/
    private  String orderVirtualID;

    /**订单 真实id**/
    private  String orderActualID;

    /**接入appid**/
    private  String appID;

    public QueryRequest() {
    }
}
