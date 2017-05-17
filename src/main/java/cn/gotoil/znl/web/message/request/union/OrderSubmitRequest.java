package cn.gotoil.znl.web.message.request.union;


import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.config.property.SybConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.14:52
 * 订单提交
 */
@Getter
@Setter
@ToString
public class OrderSubmitRequest {

    @Size(max = 2)
    private String inputCharset = "1";// 字符集 2 不可空 默认填 1；1 代表 UTF-8、2 代表 GBK、3 代表 GB2312；
    @Size(max = 100)
    private String pickupUrl= SybConstants.GateWayConsts.URL_DOPICKUP; // 付款客户的取货url 地址 100 不为空 客户的取货地址
    @Size(max = 100)
    private String receiveUrl=SybConstants.GateWayConsts.URL_RECEIVEURL;// 服务器接受支 付结果的后台 地址 100 不为空 通知商户网站支付结果的 url 地址
    @Size(max = 10)
    private String version = "v1.0";//网关接收支付 请求接口版本 10 不可空 固定填 v1.0
    @Size(max = 2)
    private String language = "1";// 网关页面显示 语言种类 2 不为空 默认填 1，固定选择值：1；1 代表简体中文、2 代 表繁体中文、3 代表英文
    @Size(max = 2)
    private String signType = "0";// 签名类型 2 不可空 默认填 0，固定选择值：0、1； 0 表示订单上送和交易结果通知都使用 MD5 进行签名
    // 1 表示商户用使用 MD5 算法验签上送订单，通联交 易结果通知使用证书签名 Asp 商户不使用通联 dll 文件签名验签的商户填 0
    private String merchantId = SybConstants.GateWayConsts.MERCHANTID;// 商户号 30 不可空 数字串，商户在通联申请开户的商户号
    @Size(max = 32)
    private String payerName;// 付款人姓名 32 可为空 跨境支付商户若采用直连模式，必须填写该值
    @Size(max = 50)
    private String payerEmail;// 付款人邮件联 系方式50 可为空 字符串
    @Size(max = 16)
    private String payerTelephone;//付款人电话联 系方式    16 可为空 数字串
    @Size(max = 22)
    private String payerIDCard;//付款人类型及 证件号     22 可为空 填写规则：证件类型+证件号码再使用通联公钥加 密。（加密请参考示例代码） 证件类型仅支持 01-身份证 跨境支付商户若采用直连模式，必须填写该值
    @Size(max = 30)
    private String pid;// 合作伙伴的商 户号30 可为空 用于商户与第三方合作伙伴拓展支付业务，Partner merchantId
    @Size(max = 50)
    private String orderNo;// 商户订单号 50 不可空 字符串，只允许使用字母、数字、- 、_,并以字母 或数字开头；每商户交的订单号，必须在当天的 该商户所有交易中唯一
    @NotNull(message = "订单金额不能为空")
    @Max(value = 9999999999l,message = "订单金额最大为9999999999")
    private long orderAmount;// 商户订单金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10   元交时金额 应为 1000      如果是美元，单位是美分，即 10 美元交时金额 为 1000
    @Size(max = 3)
    private String orderCurrency = "0";//订单金额币种 类型 3 不可空 默认填 0 0 和 156 代表人民币、840 代表美元、344 代表港币， 跨境支付商户不建议使用 0
    @Size(max = 14)
    private String orderDatetime = DateUtils.SimpleDatetimeNoConnectorFormat().format(new Date());// 商户订单交 时间 14 不可空 日期格式：yyyyMMDDhhmmss，例如：20121116020101
    @Max(value = 9999)
    private long orderExpireDatetime;// 订单过期时间 14 可为空 整形数字，单位为分钟。最大值为 9999 分钟。 如填写则以商户上送时间为准，如不填写或填 0 或填非法值，则服务端默认该订单 9999 分钟后过期，超期后不允许商户发起同一商户订单支付
    @Size(max = 256)
    private String productName;// 商品名称 256 可为空 英文或中文字符串，请勿首尾有空格字符
    private long productPrice;//商品价格 20 可为空 整型数字        //todo   bug
    private long productNum = 1; //商品数量 8 可为空 整型数字，默认传 1
    private String productId;//商品代码 20 可为空 字母、数字或- 、_ 的组合；用于使用产品数据 中心的产品数据，或用于市场活动的优惠
    private String productDesc;// 商品述 400 可为空英文或中文字符串
    private String ext1 = "";// 扩展字段 1 128 可为空 扩展字段，本字段根据商户自身的会员模式来选填 用户 ID： 如商户有会员模式，第三章注册获取用户 ID，然后
    //按照"ext1":"<USER>用户 ID</USER>"格式进行上 传。  如商户为非会员模式，则无需填写该字段。
    private String ext2 = "";//扩展字段 2 128 可为空 英文或中文字符串，支付完成后，按照原样返回给 商户
    private String extTL = ""; //业务扩展字段 1024 可为空 参见《接口技术规范文档 3.9 节介绍》
    private String payType;//支付方式 2 不可空 0 代表未指定支付方式，即显示该商户开通的所有
    // 支付方式 1 个人储蓄卡网银支付 4 企业网银支付 11 个人信用卡网银支付 23 外卡支付  28 认证支付 33 手机网页 H5 支付
    // 非直连模式，设置为 0；直连模式，值为非 0 的固 定选择值若需接入外卡支付，只支持直连模式，即固定上送 payType=23，issuerId=visa 或 mastercard
    private String issuerId;//发卡方代码 8 可为空 银行或预付卡发卡机构代码，用于指定支付使用的 付款方机构。接入手机网关时，该值留空 payType 为 0 时，issuerId 必须为空——显示该商户 支持的所有支付类型和各支付类型下支持的全部发卡机构 payType 为非 0 时，若 issuerId 为空——显示该商 户所填 payType 支付类型下的全部发卡机构 payType 为非 0 时，若 issuerId 不为空——直接跳 转到该商户所填payType下指定的发卡机构网银页 面，支持发卡机构详见附录《机构代码》
    private String pan;// 付款人支付卡 号19 可为空 数字串 如果是民生银行 B2B 直连模式，企业客户号，必填；如果是投融资行业希望支付卡号，则必填上送的卡号必须使用公钥加密(PKCS1)后进行Base64 编码。
    private String tradeNature;// 贸易类型 2 可为空 固定选择值：GOODS 或 SERVICES 当币种为人民币时选填当币种为非人民币时必填，GOODS 表示实物类型，SERVICES 表示服务类型
    private String signMsg;//签名字符串 1024 不可空 为防止非法篡改要求商户对请求内容进行签名，供 服务端进行校验；签名串生成机制：按上述顺序所有非空参数与密钥key 组合，经加密后生成 signMsg；
    private String customsExt = "";// 海关扩展字段 1024 可为空 详见“3.13.3海关扩展字段要求”章节

    @NotNull(message = "unionUserId不能为空")
    private String unionUserId;     //这个字段是为了设置ext1用的



    //先new 再 doSign
    public void doSign() throws UnsupportedEncodingException {
        //unionUserId 必填
        if (StringUtils.isEmpty(unionUserId)) return;

        StringBuilder sb = new StringBuilder("");
        sb.append("inputCharset=").append(getInputCharset())
                .append("&pickupUrl=").append(getPickupUrl())
                .append("&receiveUrl=").append(getReceiveUrl()).
                append("&version=").append(getVersion()).
                append("&language=").append(getLanguage()).
                append("&signType=").append(getSignType())
                .append("&merchantId=").append(getMerchantId());
        if (StringUtils.isNotEmpty(getPayerName())) sb.append("&payerName=").append(getPayerName());
        if (StringUtils.isNotEmpty(getPayerEmail())) sb.append("&payerEmail=").append(getPayerEmail());
        if (StringUtils.isNotEmpty(getPayerTelephone())) sb.append("&payerTelephone=").append(getPayerTelephone());
        if (StringUtils.isNotEmpty(getPayerIDCard())) sb.append("&payerIDCard=").append(getPayerIDCard());
        if (StringUtils.isNotEmpty(getPid())) sb.append("&pid=").append(getPid());
        sb.append("&orderNo=").append(getOrderNo()).
                append("&orderAmount=").append(getOrderAmount())
                .append("&orderCurrency=").append(getOrderCurrency())
                .append("&orderDatetime=").append(getOrderDatetime());
        if (StringUtils.isNotEmpty(getOrderExpireDatetime() + "")) sb.append("&orderExpireDatetime=").append(getOrderExpireDatetime());
        if (StringUtils.isNotEmpty(getProductName())) sb.append("&productName=").append(getProductName());
        if (StringUtils.isNotEmpty(getProductPrice() + "")) sb.append("&productPrice=").append(getProductPrice());
        if (StringUtils.isNotEmpty(getProductNum() + "")) sb.append("&productNum=").append(getProductNum());
        if (StringUtils.isNotEmpty(getProductId())) sb.append("&productId=").append(getProductId());
        if (StringUtils.isNotEmpty(getProductDesc())) sb.append("&productDesc=").append(getProductDesc());
        sb.append("&ext1=").append("<USER>").append(getUnionUserId()).append("</USER>");
        if (StringUtils.isNotEmpty(getExt2())) sb.append("&ext2=").append(getExt2());
        if (StringUtils.isNotEmpty(getExtTL())) sb.append("&extTL=").append(getExtTL());
        sb.append("&payType=").append(getPayType());
        if (StringUtils.isNotEmpty(getIssuerId())) sb.append("&issuerId=").append(getIssuerId());
        if (StringUtils.isNotEmpty(getPan())) sb.append("&pan=").append(getPan());
        if (StringUtils.isNotEmpty(getTradeNature())) sb.append("&tradeNature=").append(getTradeNature());
        sb.append("&key=").append(SybConstants.GateWayConsts.MERCHANTKEY);
//        System.out.println(sb);
        this.ext1  = "<USER>"+getUnionUserId()+"</USER>";
        this.setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());
    }



}
