package cn.gotoil.znl.web.message.request.union;


import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.znl.config.property.SybConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.10:48
 * 订单退款请求
 */
public class RefundRequest {
    public static final String defaultVersion ="v2.3";

    private String version =defaultVersion;// 网关联机退 款接口版本4 不可空 固定值：v2.3
    private String signType ="0" ;//签名类型 1 不可空 0 表示订单上送和交易结果通知都使用 MD5 进行签名 1 表示商户用使用 MD5 算法验签上送订 单，通联交易结果通知使用证书签名
    private String merchantId = SybConstants.GateWayConsts.MERCHANTID ;//商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String orderNo ; //商户订单号 50 不可空 字母、数字、-、_ 及其组合，与交订单 时的商户订单号保持一致
    private long  refundAmount;// 退款金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10 元交 时金额应为 1000 如果是美元，单位是美分，即10 美元交时金额为1000 系统支持部分退款，但需保证退款金额<= 订单金额
    private String mchtRefundOrderNo ;//商户退款订 单号 50 可空 字母、数字、-、_ 及其组合， 如果填写，退款结果原样返回
    private String  orderDatetime  ;//商户订单 交时间 14 不可空 数字串，与交订单时的商户订单交时 间保持一致
    private String signMsg ;//签名字符串 1024不可空以上所有非空参数按上述顺序与密钥 key 组合，经加密后生成该值。


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


    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    @Override
    public String toString() {
        return "RefundRequest{" +
                "version='" + version + '\'' +
                ", signType='" + signType + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", refundAmount=" + refundAmount +
                ", mchtRefundOrderNo='" + mchtRefundOrderNo + '\'' +
                ", orderDatetime='" + orderDatetime + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }


    public void doSign() throws Exception{

        StringBuilder sb  =  new StringBuilder();
        sb.append("version=").append(getVersion())
                .append("&signType=").append(getSignType())
                .append("&merchantId=").append(getMerchantId())
                .append("&orderNo=").append(getOrderNo())
                .append("&refundAmount=").append(getRefundAmount()) ;
        if(StringUtils.isNotEmpty(getMchtRefundOrderNo())) sb.append("&mchtRefundOrderNo=") .append(getMchtRefundOrderNo());
        sb.append("&orderDatetime=").append(getOrderDatetime()) ;
        sb.append("&key=").append(SybConstants.GateWayConsts.MERCHANTKEY)      ;
        setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());

    }
}
