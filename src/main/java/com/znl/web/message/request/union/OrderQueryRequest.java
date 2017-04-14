package com.znl.web.message.request.union;

import com.znl.common.tools.date.DateUtils;
import com.znl.common.tools.encoder.Hash;
import com.znl.config.property.SybConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/10.8:43
 * 单笔订单查询请求
 */
public class OrderQueryRequest {

    public static final String defaultVersion  = "v1.5"  ;
    public static final String defaultQueryDateTime=  DateUtils.SimpleDatetimeNoConnectorFormat().format(new Date());

    private String merchantId;//商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String version =defaultVersion;//  网关查询接 口版本0 不可空 固定值：v1.5
    private String signType = "0";// 签名类型 2 不可空 固定选择值：0、1；与客户交订单填写的值保持一 致
    private String orderNo;//  商户订单号 50 不可空 字母、数字、-、_ 及其组合，与交订单时的商户订 单号保持一致
    private String orderDatetime;//商户订单 交时间 14 不可空数字串，与交订单时的商户订单交时间保持一致， 仅支持查询(当前时间-31 天)以内的订单
    private String queryDatetime =defaultQueryDateTime;// 商户交查 询的时间 14 不可空 此时间不能与系统当前时间相差 15 分钟
    private String signMsg;// 签名字符串 1024 不可空 以上所有非空参数按上述顺序与密钥 key 组合，经加 密后生成该值。


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getQueryDatetime() {
        return queryDatetime;
    }

    public void setQueryDatetime(String queryDatetime) {
        this.queryDatetime = queryDatetime;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public void doSign() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder("");
        if(StringUtils.isEmpty(getMerchantId())) setMerchantId(SybConstants.GateWayConsts.MERCHANTID);
        sb.append("merchantId=").append(getMerchantId())
                .append("&version=").append(getVersion())
                .append("&signType=").append(getSignType())
                .append("&orderNo=").append(getOrderNo())
                .append("&orderDatetime=").append(getOrderDatetime())
                .append("&queryDatetime=").append(getQueryDatetime())
                .append("&key=").append(SybConstants.GateWayConsts.MERCHANTKEY);
        setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());
    }


    @Override
    public String toString() {
        return "OrderQueryRequest{" +
                "merchantId='" + merchantId + '\'' +
                ", version='" + version + '\'' +
                ", signType='" + signType + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderDatetime='" + orderDatetime + '\'' +
                ", queryDatetime='" + queryDatetime + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }
}
