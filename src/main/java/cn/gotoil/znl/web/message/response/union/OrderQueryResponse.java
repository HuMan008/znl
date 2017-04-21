package cn.gotoil.znl.web.message.response.union;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.8:53
 * 订单查询结果响应
 */
@Getter
@Setter
@ToString
public class OrderQueryResponse {

     private String payDatetime;
     private String userName;
    private String credentialsType;
    private String pan;
    private String txOrgId;
    private String ext1;
    private String payAmount;
    private String returnDatetime;
    private String credentialsNo;
    private String issuerId;
    private String signMsg  ;
    private String payType ;
    private String language;
    private String errorCode;
    private String merchantId;
    private String orderDatetime  ;
    private String version;
    private String orderNo;
    private String ext2;
    private String signType;
    private String orderAmount;
    private String extTL;
    private String paymentOrderId;
    private String payResult;


}
