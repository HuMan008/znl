package cn.gotoil.znl.web.message.response.union;

import cn.gotoil.bill.tools.encoder.Hash;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/10.8:19
 * 支付结果响应 （通联SDK、H5）
 */
@Setter
@Getter
@ToString
public class PayResultResponse {

private String merchantId ; //商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String  version; //网关返回支付结 果接口版本 10 不可空 固定选择值：v1.0；注意为小写字母
    private String language ;// 网页显示语言种类  2 可空 固定值：1；1 代表中文显示
    private String signType ;//签名类型 2 不可空 固定选择值：0、1，与交订单时的签名类型保持一 致
    private String payType ;//支付方式 2 可空 字符串，与交订单时的支付方式一致
    private String issuerId ;// 发卡方机构代码 8 可空 固定为空
    private String paymentOrderId ;// 通联订单号 50 不可空 字符串，通联订单号
    private String orderNo ; //商户订单号 50 不可空 字符串，与交订单时的商户订单号保持一致
    private String orderDatetime;// 商户订单交时间14 不可空 数字串，与交订单时的商户订单交时间保持一致
    private long orderAmount ;//商户订单金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10 元交时金额应为 1000如果是美元，单位是美分，即 10 美元交时金额为1000
    private String  payDatetime;// 支付完成时间 14 不可空 日期格式：yyyyMMDDhhmmss，例如： 20121116020101
    private long  payAmount ;// 订单实际支付金 额 10 不可空 整型数字，实际支付金额，用户实际支付币种为人民 币； 以分为单位，例如 10 元返回时应为 1000 分
    private String  ext1 ;//扩展字段 1 128 可空 字符串，与交订单时的扩展字段 1 保持一致
    private String ext2 ;// 扩展字段 2 128 可空 字符串，与交订单时的扩展字段 2 保持一致
    private String payResult;// 处理结果 2 不可空 1：支付成功 仅在支付成功时通知商户。 商户可以通过查询接口查询订单状态。
    private String  errorCode ;//错误代码 10 可空 固定为空
    private String returnDatetime ;//结果返回时间 14 不可空 系统返回支付结果的时间，日期格式： yyyyMMDDhhmmss
    private String signMsg ;// 签名字符串 1024 不可空 以上所有非空参数按上述顺序与密钥组合，经加密后 生成该值。



    public String doSign(String key)  throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("merchantId=").append(getMerchantId())
                .append("&version=") .append(getVersion()) ;
                if(StringUtils.isNotEmpty(getLanguage()))    {
                    sb.append("&language=").append(getLanguage());
                }
        sb.append("&signType=").append(getSignType()) ;
        if(StringUtils.isNotEmpty(getPayType())) {
            sb.append("&payType=").append(getPayType()) ;
        }
        if(StringUtils.isNotEmpty(getIssuerId())){
            sb.append("&issuerId=").append(getIssuerId()) ;
        }
        sb.append("&paymentOrderId=").append(getPaymentOrderId()).append("&orderNo=").append(getOrderNo());
        sb.append("&orderDatetime=").append(getOrderDatetime()) .append("&orderAmount=").append(getOrderAmount())
                .append("&payDatetime=").append(getPayDatetime()) .append("&payAmount=").append(getPayAmount())     ;
        if(StringUtils.isNotEmpty(getExt1())){
            sb.append("&ext1=").append(getExt1());
        }
        if(StringUtils.isNotEmpty(getExt2())){
            sb.append("&ext2=").append(getExt2())   ;
        }
        sb.append("&payResult=").append(getPayResult()) ;
        if(StringUtils.isNotEmpty(getErrorCode())){
            sb.append("&errorCode=").append(getErrorCode());
        }
        sb.append("&returnDatetime=").append(getReturnDatetime())  ;
        sb.append("&key=").append(key);


        return  Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase();
    }


}
