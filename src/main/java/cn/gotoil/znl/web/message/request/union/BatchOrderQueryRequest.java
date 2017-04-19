package cn.gotoil.znl.web.message.request.union;

import cn.gotoil.bill.tools.encoder.Hash;
import cn.gotoil.znl.config.property.SybConstants;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/11.9:15
 */
public class BatchOrderQueryRequest {


    public static final String defaultVersion ="v1.6";

    public static final String defaultMerchantId = SybConstants.GateWayConsts.MERCHANTID;

    private String version=defaultVersion; //网关批量查询接口版本 10 不可空 固定值：v1.6
    private String merchantId = defaultMerchantId;// 商户号 30 不可空 数字串，与交订单时的商户号保持一致
    private String  beginDateTime  ; //查询订单的 开始时间 14 不可空 与 endDateTime 必须为同一天，日期填写格式： yyyymmddhh，例如：2013011600，目前只支持对当天订单 进行查询
    private String  endDateTime  ; //endDateTime 查询订单的 结束时间 14 不可空 与 beginDateTime 必须为同一天，日期填写格式： yyyymmddhh，例如：2013011623，查询时间范围为当天的 00:00:00~23:59:59
    private int pageNo =1 ; //查询页码 2 不可空 从 1 开始，必须为数字
    private String signType="1";// 签名类型 2 不可空 固定值：1
    private String signMsg ;// 签名字符串 1024 不可空 以上所有非空参数按上述顺序与密钥 key 组合，经加密后 生成该值。

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    @Override
    public String toString() {
        return "BatchOrderQueryRequest{" +
                "version='" + version + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", beginDateTime='" + beginDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", pageNo=" + pageNo +
                ", signType='" + signType + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }

    public void doSign() throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("version=").append(getVersion())
                .append("&merchantId=").append(getMerchantId())
                .append("&beginDateTime=").append(getBeginDateTime())
                .append("&endDateTime=").append(getEndDateTime())
                .append("&pageNo=").append(getPageNo())
                .append("&signType=").append(getSignType())
                .append("&key=").append(SybConstants.GateWayConsts.MERCHANTKEY);
        setSignMsg(Hash.md5(new String(sb.toString().getBytes("utf-8"))).toUpperCase());


    }
}
