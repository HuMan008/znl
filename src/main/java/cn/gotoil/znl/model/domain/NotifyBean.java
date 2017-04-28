package cn.gotoil.znl.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/26.10:45
 */
@Getter
@Setter
@ToString
public class NotifyBean {
    private String acct;
    private String appid;
    private String chnltrxid;
    private String cusid;
    private String cusorderid;
    private String outtrxid;
    private String paytime;
    private String sign;
    private String termauthno;
    private String termrefnum;
    private String termtraceno;
    private String trxamt;
    private String trxcode;
    private String trxdate;
    private String trxid;
    private String trxstatus;
    private String trxreserved;
}
