package cn.gotoil.znl.model.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 支付日志
 */
@Entity
@Table(name = "dk_pay_log")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class PayLog extends  BaseEntity    {

    /**
     * 主键
     */
    @Id
    @Column( unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 应用id
     */
    @Column( name="appid",columnDefinition = "char(32)  COMMENT '应用id'" ,nullable = false )
    private String appID;

    /**
     * 用户id
     */
    @Column( name="uid",columnDefinition = "char(50)  COMMENT '用户id'" ,nullable = false )
    private String userID;

    /**
     * 订单号
     */
    @Column( name="orderno",columnDefinition = "char(50)  COMMENT '订单号'" ,nullable = false )
    private String orderNo;

    /**
     * 商品id
     */
    @Column( name="productId",columnDefinition = "char(50)  COMMENT '商品id'" ,nullable = true )
    private String productID;


    /**
     * 支付方式
     */
    @Column( name="paytype" , nullable = false )
    private Byte payType;

    /**
     * 支付金额
     */
    @Column( nullable = false )
    private Integer amount;


    /**
     * 支付时间
     */
    @Column( nullable = false , name = "paytime" ,updatable = false  )
    private Date payTime;

    /**
     * 支付结果
     */
    @Column(  nullable = false )
    private Byte state;



    public PayLog() {

    }

}
