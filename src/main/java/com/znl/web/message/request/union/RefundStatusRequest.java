package com.znl.web.message.request.union;

import com.znl.common.tools.encoder.Hash;
import com.znl.config.property.SybConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.14:20
 * 退款状态查询请求
 */
public class RefundStatusRequest {



    public static final String defaultVersion ="v2.4"  ;

    private String version=defaultVersion ;//退款查询版本4 不可空 固定值：v2.4
    private String signType="0" ;//  签名类型 1 不可空 0 表示订单上送和结果返回都使 用 MD5 进行签名 1表示商户用使用MD5算法签名上送订单，通联返回使用证书签名
    private String merchantId=SybConstants.GateWayConsts.MERCHANTID; //商户号 30 不可空 数字串，与交订单时的商户号 保持一致
    private String  orderNo ;// 商户订单号 50 不可空 字母、数字、-、_ 及其组合，与 交订单时的商户订单号保持一 致
    private long  refundAmount;//  退款金额 10 不可空 整型数字,单位是分系统支持部分退款，但需保证退款金额<=订单金额
    private String  refundDatetime ;// 退款受理时 间 14 可空 数字串、退款申请受理的时间日 期格式：yyyyMMDDhhmmss 如 20121116143030
    private String mchtRefundOrderNo;// 商户退款订 单号50 可空 字母、数字、-、_ 及其组合，在 商户的系统里是唯一，不可重复
    private String signMsg;// 签名字符串 1024 不可空 以上所有非空参数按上述顺序与 密钥 key 组合，经加密后生成该值。

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundDatetime() {
        return refundDatetime;
    }

    public void setRefundDatetime(String refundDatetime) {
        this.refundDatetime = refundDatetime;
    }

    public String getMchtRefundOrderNo() {
        return mchtRefundOrderNo;
    }

    public void setMchtRefundOrderNo(String mchtRefundOrderNo) {
        this.mchtRefundOrderNo = mchtRefundOrderNo;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    @Override
    public String toString() {
        return "RefundStatusRequest{" +
                "version='" + version + '\'' +
                ", signType='" + signType + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", refundAmount=" + refundAmount +
                ", refundDatetime='" + refundDatetime + '\'' +
                ", mchtRefundOrderNo='" + mchtRefundOrderNo + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }

    public void doSign() throws Exception{
        StringBuilder sb  =  new StringBuilder();
        sb.append("version=").append(getVersion())
                .append("&signType=").append(getSignType())
                .append("&merchantId=").append(getMerchantId())
                .append("&orderNo=") .append(getOrderNo())
                .append("&refundAmount=").append(getRefundAmount()) ;
        if(StringUtils.isNotEmpty(getRefundDatetime())) sb.append("&refundDatetime=").append(getRefundDatetime());
        if(StringUtils.isNotEmpty(getMchtRefundOrderNo())) sb.append("&mchtRefundOrderNo=").append(getMchtRefundOrderNo());
        sb.append("&key=").append(SybConstants.GateWayConsts.MERCHANTKEY)  ;
        setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());

    }
}
