package cn.gotoil.znl.web.message.request.union;

import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.bill.web.annotation.ThirdValidation;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.config.property.SybConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/27.9:50
 */
@Getter
@Setter
@ToString
public class AppPayRequest {

    private static final String defaultVersion ="v1.0";
    private static final String defaultLanguage = "1";
    private static final String defaultSignType = "0";

    @ThirdValidation(regex = "^1|2|3$" ,message = "inputChartset Vaule Error,Only accept [1,2,3]")
    private  String inputCharset="1";// String 必填协议字符集。固定选择值：1、2、3 1代表UTF-8、2代表GBK、3代表GB2312

    private String receiveUrl= SybConstants.SDK.URL_APPRECEIVEURL;// String
    // 必填支付通知结果以此为准，后台通知商户网站支付结果的url地址。无论是测试环境还是生产环境，请商户确认此url地址可被访问。特别是生产环境下，需要商户端的网络配置开通。否则无法接收到通联发送的支付结果通知导致调单。


    private String version=defaultVersion ;// String 必填 协议版本，固定填v1.0

    @ThirdValidation(regex = "^1|2|3$" ,message = "language Vaule Error,Only accept [1,2,3]")
    private String language =defaultLanguage;// String 选填指定支付插件当前显示语言，境内商户默认1，仅国际卡支 付有效。 固定选择值：1、2、3  1代表简体中文、2代表繁体中文、3代表英文

    @ThirdValidation(regex = "^0|1$",message = "signType Vaule Error,Only accept [0,1]")
    private String signType=defaultSignType ;// String 必填 默认填1，固定选择值：0、1 0 表示订单上送和交易结果通知都使用MD5进行签名 1 表示商户用使用MD5算法验签上送订单，通联交易结果 通知使用证书签名
    // 若signType为1，则使用通联的公钥证书进行验签，调用通联提供的allinpay-security-xxx.jar中的verify()方法。

    private String merchantId=SybConstants.SDK.MERCHANTID ;//String 必填 调用通联支付服务的商户号


    private String orderNo ;//String 必填 生成的订单号
    @Min(value = 1 ,message = "orderAmount min 1")
    @Max(value = Integer.MAX_VALUE,message = "orderAmount max "+Integer.MAX_VALUE)
    private int orderAmount;// Number 必填整型数字，金额与币种有关 单位是分，即10元提交时金额应为1000

    @ThirdValidation(regex = "^0|156$" ,message = "orderCurrency Vaule Error,Only accept [0,156]")
    private String orderCurrency ="0"; // String 必填 币种限定为: 0或156 – 人民币 商户在填写该字段的时候请先确认当前使用的商户号开通的支付交易类型。

    private String orderDatetime = DateUtils.SimpleDatetimeNoConnectorFormat().format(new Date());//String  必填 订单生成的时间
    @Max(180)
    private int orderExpireDatetime=30 ;//Number 选填  订单过期时间整形数字，单位为分钟。最大值为180分钟。 如填写则以商户上送时间为准，如不填写或填0或填非法值，则服务端默认该订单180分钟后过期，超期后不允许商户发起同一商户订单支付

    private String productName ="" ;//String 必填 商品名称

    private String ext1 ;//String 选填本字段根据商户自身的会员模式来选填用户userId：如商户 有会员模式，则需先通过后台注册通联唯一userId获取，然后按照"ext1":"<USER>userId</USER>"格式进行上传。如商户为非会员模式，则无需填写该字段。说明：SDK标准版、SDK刷脸版、H5版均需商户是会员模式，对接这些版本需必填

    private String  ext2 ;//  String 选填 扩展字段，支付结果中原样返回

    private String extTL ;// String 选填 参见《接口技术规范文档4.9节介绍》


    private String payType ="33" ;// String 必填支付类型说明：填0，表示向持卡人展示所有商户开通的可用支付类型，并由持卡人选择。填非0，表示不展示商户开通的可用支付类型，非0可填数值及描述如下：
/*            填27，表示直接使用认证支付。若商户此时开通跨境
    支付则支持跨境支付。（适用于v2.0或以上版的控件）；
            填30， 则表示直接使用国际卡支付，支持Visa，
    MasterCard（适用于v2.4.0或以上版的控件）；
            填33，则表示使用新移动支付产品（适用于V4.1、
            4.3版本）*/
    @ThirdValidation(regex = "^\\s*|visa|mastercard$" ,message = "issuerId value error,can accept[nullString,visa,mastercard]")  //要么是空 要么是 这两个
    private String issuerId="" ; //String 选填指定卡组织类型，仅国际卡支付有效。当前可传值 : visa、mastercard
    @ThirdValidation(regex = "^\\s*|GOODS|SERVICES$",message = "tradeNature value error,can accept[nullString,GOODS,SERVICES]")
    private String  tradeNature ="";// String 选填指定贸易类型，跨境与国际卡支付请填写该字段 当币种为人民币时选填，当币种为非人民币时必填 固定选择值：GOODS或SERVICES GOODS表示实物贸易，SERVICES表示服务贸易
    private String signMsg ;// String 必填为防止非法篡改要求商户对请求内容进行签名，供服务端进行校验。签名串生成机制：按上述顺序所有非空参数与密钥key组合，经加密后生成signMsg；具体参考5签名机制

    private String cardNo="";//String 选填 适用境内商户支付，不适用外卡支付和跨境支付 商户指定支付的银行卡号，则需要上送此字段。否则无需上 送该字段。切记：该字段不参与signMsg的计算。

    @NotNull(message = "unionUserId不能为空")
    private String unionUserId;     //这个字段是为了设置ext1用的


    public void doSign() throws Exception{
        this.ext1  = "<USER>"+getUnionUserId()+"</USER>";
        StringBuilder sb = new StringBuilder();
        sb.append("inputCharset=").append(getInputCharset())
                .append("&receiveUrl=").append(getReceiveUrl())
                .append("&version=").append(getVersion())
                .append("&language=").append(getLanguage())
                .append("&signType=").append(getSignType())
                .append("&merchantId=") .append(getMerchantId())
                .append("&orderNo=") .append(getOrderNo())
                .append("&orderAmount=").append(getOrderAmount())
                .append("&orderCurrency=").append(getOrderCurrency())
                .append("&orderDatetime=").append(getOrderDatetime());
        if(StringUtils.isNotEmpty(getOrderExpireDatetime()+""))  sb.append("&orderExpireDatetime=").append(getOrderExpireDatetime())  ;
        sb.append("&productName=").append(getProductName());
        sb.append("&ext1=").append("<USER>").append(getUnionUserId()).append("</USER>");
        if(StringUtils.isNotEmpty(getExt2()))  sb.append("&ext2=").append(getExt2())  ;
        if(StringUtils.isNotEmpty(getExtTL()))  sb.append("&extTL=").append(getExtTL())  ;
        sb.append("&payType=") .append(getPayType())   ;
        if(StringUtils.isNotEmpty(getIssuerId()))  sb.append("&issuerId=").append(getIssuerId())  ;
        if(StringUtils.isNotEmpty(getTradeNature()))  sb.append("&tradeNature=").append(getTradeNature())  ;
        sb.append("&key=").append(SybConstants.SDK.MERCHANTKEY) ;

        System.out.println(sb.toString());

        this.setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());


    }


    public void doSign(String key) throws UnsupportedEncodingException {
        this.ext1  = "<USER>"+getUnionUserId()+"</USER>";
        StringBuilder sb = new StringBuilder();
        sb.append("inputCharset=").append(getInputCharset())
                .append("&receiveUrl=").append(getReceiveUrl())
                .append("&version=").append(getVersion())
                .append("&language=").append(getLanguage())
                .append("&signType=").append(getSignType())
                .append("&merchantId=") .append(getMerchantId())
                .append("&orderNo=") .append(getOrderNo())
                .append("&orderAmount=").append(getOrderAmount())
                .append("&orderCurrency=").append(getOrderCurrency())
                .append("&orderDatetime=").append(getOrderDatetime());
        if(StringUtils.isNotEmpty(getOrderExpireDatetime()+""))  sb.append("&orderExpireDatetime=").append(getOrderExpireDatetime())  ;
        sb.append("&productName=").append(getProductName());
        sb.append("&ext1=").append("<USER>").append(getUnionUserId()).append("</USER>");
        if(StringUtils.isNotEmpty(getExt2()))  sb.append("&ext2=").append(getExt2())  ;
        if(StringUtils.isNotEmpty(getExtTL()))  sb.append("&extTL=").append(getExtTL())  ;
        sb.append("&payType=") .append(getPayType())   ;
        if(StringUtils.isNotEmpty(getIssuerId()))  sb.append("&issuerId=").append(getIssuerId())  ;
        if(StringUtils.isNotEmpty(getTradeNature()))  sb.append("&tradeNature=").append(getTradeNature())  ;
        sb.append("&key=").append(key) ;

        this.setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());


    }
}
