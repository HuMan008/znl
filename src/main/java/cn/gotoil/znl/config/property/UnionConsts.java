package cn.gotoil.znl.config.property;

/**
 * Created by think on 2017/5/16.
 * 通联的一些常亮设置
 */
public class UnionConsts {

    public static class SDK{
        public  static String notifyUrl  ;
    }

    public static class GateWay{
        //	用户注册请求接口
        public static  String register ;

        /**
         * 订单提交
         * 接入互联网网关地址：
         * 测试环境：http://ceshi.allinpay.com/gateway/index.do，H5 请直接在生产环境调试
         * B2C/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         * H5 生产环境：https://cashier.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action
         */
        public static  String orderSubmit ;

        /**
         * 订单查询（单个）
         * 测试环境：http://ceshi.allinpay.com/gateway/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         */
        public static  String  orderQuery ;


        /**
         * 订单查询（批量）
         * 测试环境：http://ceshi.allinpay.com/mchtoq/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/mchtoq/index.do
         */
        public static  String   orderBatchQuery;


        /**
         * 退款申请
         *    测试环境：http://ceshi.allinpay.com/gateway/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         */
        public static  String refund ;


        /**
         * 退款状态查询
         * 测试环境：http://ceshi.allinpay.com/mchtoq/refundQuery B2C/H5/B2B/WAP
         * 生产环境：https://cashier.allinpay.com/mchtoq/refundQuery
         */
        public static  String  refundStatusQuery;

        public static String   pick;    //通联支付同步通知
        public static String     receive;       //通联支付异步通知
    }

    public static class WechatJs{
        //    https://api.weixin.qq.com/sns/oauth2/access_token
        public static  String sessionHost;

        //    authorization_code
        public static String grantType;


        //    通知地址
        public static String notifyUrl;
    }

}
