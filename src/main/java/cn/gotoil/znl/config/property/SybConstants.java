package cn.gotoil.znl.config.property;

/**
 * 通联参数配置
 * 如果需要修改 修改application.yml
 */
public class SybConstants {
    public static  String SYB_CUSID ;
    public static  String SYB_APPID ;
    public static  String SYB_APPKEY;
    public static  String SYB_APIURL;


    //网关接口配置
    public static class GateWayConsts {

        public static String MERCHANTID ;
        public static String MERCHANTKEY;
        //	用户注册请求接口
        public static  String URL_UNIONREGISTER ;

        /**
         * 订单提交
         * 接入互联网网关地址：
         * 测试环境：http://ceshi.allinpay.com/gateway/index.do，H5 请直接在生产环境调试
         * B2C/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         * H5 生产环境：https://cashier.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action
         */
        public static  String URL_ORDERSUBMIT ;

        /**
         * 订单查询（单个）
         * 测试环境：http://ceshi.allinpay.com/gateway/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         */
        public static  String URL_ORDERQUERY ;


        /**
         * 订单查询（批量）
         * 测试环境：http://ceshi.allinpay.com/mchtoq/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/mchtoq/index.do
         */
        public static  String URL_ORDERQUERYBATCH ;


        /**
         * 退款申请
         *    测试环境：http://ceshi.allinpay.com/gateway/index.do
         * B2C/H5/B2B/WAP 生产环境：https://cashier.allinpay.com/gateway/index.do
         */
        public static  String URL_REFUND ;


        /**
         * 退款状态查询
         * 测试环境：http://ceshi.allinpay.com/mchtoq/refundQuery B2C/H5/B2B/WAP
         * 生产环境：https://cashier.allinpay.com/mchtoq/refundQuery
         */
        public static  String URL_REFUNDSTATUS ;

        public static String   URL_DOPICKUP;    //通联支付同步通知
        public static String     URL_RECEIVEURL;       //通联支付异步通知




    }
    public static class SDK{
        public static String MERCHANTID ;
        public static String MERCHANTKEY;
        public static String URL_APPRECEIVEURL ;// 移动支付回调
    }



}
