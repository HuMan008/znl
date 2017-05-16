package cn.gotoil.znl.model.domain;


import cn.gotoil.znl.web.message.Combobox;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付日志
 */
@Entity
@Table(name = "dk_pay_log")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class PayLog  implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column( unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 订单id(虚拟订单id)
     */
    @Column( length = 50,nullable = false )
    private String orderid;

    /**
     * 日志类型
     */
    @Column( name="log_type" , nullable = false )
    private Byte logType;

    /**
     * 请求报文
     */
    @Column(  nullable = false , length = 1000)
    private String content;


    /**
     * 创建时间
     */
    @Column(  nullable = false , name = "created_at" )
    private Date createdAt;

    public PayLog() {

    }

    public enum LogTypeEnum {

        /**
         *  1、支付
         2、订单查询
         3、支付结果查询
         4、同步通知
         5、异步通知
         */
        Pay( (byte)1,"支付"),
        OrderSearch( (byte)2,"订单查询"),
        ResultSearch( (byte)3,"支付结果查询"),
        SyncNotify( (byte)4,"同步通知"),
        ASyncNotify( (byte)5,"异步通知"),

        ;

        private final Byte code;
        private final String name;


        LogTypeEnum(Byte code,String name ){
            this.code = code;
            this.name = name;
        }

        /**
         *  转换类型
         *  @return
         */
        public static LogTypeEnum getEnumByCode(byte code) {

            for (   LogTypeEnum en :   LogTypeEnum.values()) {
                if ( code == en.getCode() ) {
                    return en;
                }
            }
            return null;
        }

        public byte getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

}
