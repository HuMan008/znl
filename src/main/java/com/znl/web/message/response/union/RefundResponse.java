package com.znl.web.message.response.union;

import com.znl.config.property.SybConstants;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.10:54
 * 订单申请退款响应
 */
public class RefundResponse {

    private  final static  String defaultVersion = "v2.3";

    private  String  merchantId = SybConstants.GateWayConsts.MERCHANTID; //商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String version = defaultVersion ;// 网关联机退款 接口版本 10 不可空 固定值：v2.3
    private String signType="0"; // 签名类型 2 不可空 固定选择值：0、1；与客户交请求填写的值保持 一致
    private String orderNo ;// 商户订单号 50 不可空 字母、数字、-、_ 及其组合，与交订单时的商 户订单号保持一致
    private long orderAmount ;// 商户订单金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10 元交时金额 应为 1000 如果是美元，单位是美分，即 10 美元交时金额 为 1000
    private String orderDatetime;// 商户订单交 时间 14 不可空 数字串，与交订单时的商户订单交时间保持一 致
    private long   refundAmount ;// 退款金额 10 不可空 整型数字，金额与币种有关 如果是人民币，则单位是分，即 10 元交时金额 应为 1000 如果是美元，单位是美分，即 10 美元交时金额 为 1000
    private String refundDatetime;// 退款受理时间 14 不可空 数字串、退款申请受理的时间日期格式： yyyyMMDDhhmmss 如 20121116143030
    private String refundResult;// 退款结果 10 不可空 申请成功：20  其他为失败
    private String mchtRefundOrderNo ;//商户退款订单号50 可空 字母、数字、-、_ 及其组合， 如请求有填写，退款结果原样返回
    private String returnDatetime ;// 结果返回时间 14 不可空 数字串、退款申请完成的时间日期格式： yyyyMMDDhhmmss 如 20121116143030
    private String signMsg;// 签名字符串 1024 不可空 以上所有非空参数按上述顺序与密钥组合，经加密 后生成该值。




}
