package cn.gotoil.znl.model.domain;

import cn.gotoil.znl.web.message.Combobox;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  操作日志
 */
@Entity
@Table(name = "dk_order")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class Order  extends  BaseEntity{


    /**
     *
     */
    @Id
    @Column( name="id",length = 50 ,nullable = false )
    private String ID;

    /**
     *
     */
    @Column( name="appid",length = 32 ,nullable = false )
    private String appid;

    /**
     * 应用用户id[ 接入应用系统内部的 支付发起者id]
     */
    @Column( name="appuser_id",length = 50   )
    private String appuserID;

    /**
     *  第三方订单编号
     */
    @Column( name="third_order_no",length = 50    )
    private String thirdOrderNO;

    /**
     *  应用实际订单号
     */
    @Column( name="app_order_id",length = 50 ,nullable = false   )
    private String appOrderID;

    /**
     *  订单失效时间(相对值)【分钟】
     */
    @Column( name="expire_time_minute"  ,nullable = false   )
    private Integer  expire_time_minute;

    /**
     * 订单失效时间（绝对值）
     */
    @Column(  name="order_expire_time" ,nullable = false ,updatable = false )
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderExpireTime;

    /**
     * 订单最新的状态
     */
    @Column( name="status"  ,nullable = false )
    private Byte status;

    /**
     * 订单最新状态 对应 的更新时间
     */
    @Column( name="status_update_time"  ,nullable = false )
    private Date statusUpdateTime;

    /**
     * 订单 预支付 金额（分）
     */
    @Column( name="order_fee"  , nullable = false )
    private Integer orderFee;

    /**
     * 订单 实际支付 金额（分）
     */
    @Column( name="order_actual_fee"  , nullable = false )
    private Integer orderActualFee;

    /**
     * 额外参数(json string)
     */
    @Column( name="extra_param" ,length = 500  )
    private String extraParam;


    /**
     * 备注
     */
    @Column( name="remark" ,length = 200  )
    private String remark;


    /**
     * 订单描述
     */
    @Column( name="descp" ,length = 200  )
    private String descp;

    /**
     * 支付方式
     */
    @Column( name="pay_type" ,length = 15 , nullable = false )
    private String payType;

    /**
     * 收款账户id
     */
    @Column( name="pay_account_id"  ,nullable = false )
    private Integer payAccountID;

    /**
     *  异步回调地址
     */
    @Column(name="notify_url",length = 500,nullable = false )
    private String notifyUrl;

    /**
     *   同步 通知地址
     */
    @Column(name="return_url",length = 500,nullable = false )
    private String returnUrl;

    /**
     * 版本号
     */
    @Version
    @Column( name="v"  ,nullable = false )
    private Long version;

    public Order() {
    }


    public enum StateEnum {

        /**
         *  发起 支付请求【支付中】
         */
        Paying((byte)1,"支付中"),

        /**
         *  支付成功
         */
        PayedSuccess((byte)2,"支付成功"),

        /**
         *  支付失败，已放弃当前订单
         */
        GiveUp_Failed((byte)8,"支付失败"),

        /**
         *  支付超时，已放弃当前订单
         */
        GiveUp_OverTime((byte)9,"支付超时"),
        ;

        private final byte code;
        private final String name;


        StateEnum(Byte code,String name){
            this.code = code;
            this.name = name;
        }


        /**
         *  转换类型
         *  @return
         */
        public static  StateEnum getNameByCode(Byte code) {
            if(null == code){
                return  null;
            }
            for (   StateEnum en :    StateEnum.values()) {
                if ( en.getCode() == code ) {
                    return en;
                }
            }
            return null;
        }

        public static List<Combobox> getEnumList(){

            List<Combobox> list = new ArrayList<Combobox>();

            Combobox boxNone = new Combobox();
            boxNone.setCode(null);
            boxNone.setName( "--请选择--");
            list.add( boxNone );

            for(   StateEnum type:    StateEnum.values()){
                Combobox box = new Combobox();
                box.setCode( type.getCode()+"" );
                box.setName( type.getName() );
                list.add( box );
            }
            return list;
        }

        public byte getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum PayTypeEnum {

        /**
         *  支付宝-SDK
         */
        Zhifubao_SDK( "ZFB_SDK","支付宝-SDK",AccountAlipaySDK.class),


        /**
         *  支付宝-wap
         */
        Zhifubao_WAP( "ZFB_WAP","支付宝-wap",AccountForAlipayWAP.class),
        ;

        private final String code;
        private final String name;
        private final Class table;


        PayTypeEnum(String code,String name,Class table){
            this.code = code;
            this.name = name;
            this.table = table;
        }

        /**
         *  转换类型
         *  @return
         */
        public static  PayTypeEnum getEnumByCode(String code) {
            if(null == code){
                return  null;
            }
            for (   PayTypeEnum en :   PayTypeEnum.values()) {
                String codeStr = en.getCode();
                if ( codeStr.equals(code) ) {
                    return en;
                }
            }
            return null;
        }

        /**
         *  转换类型
         *  @return
         */
        public static  String getNameByCode(String code) {
            if(null == code){
                return  null;
            }
            for (   PayTypeEnum en :   PayTypeEnum.values()) {
                if ( en.getCode().equals(code) ) {
                    return en.getName();
                }
            }
            return "";
        }

        public static List<Combobox> getEnumList(){

            List<Combobox> list = new ArrayList<Combobox>();

            Combobox boxNone = new Combobox();
            boxNone.setCode( null );
            boxNone.setName( "--请选择--");
            list.add( boxNone );

            for(   PayTypeEnum type:    PayTypeEnum.values()){
                Combobox box = new Combobox();
                box.setCode( type.getCode()+"" );
                box.setName( type.getName() );
                list.add( box );
            }
            return list;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Class getTable() {
            return table;
        }
    }


}
