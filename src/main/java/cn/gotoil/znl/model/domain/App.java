package cn.gotoil.znl.model.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.gotoil.znl.web.message.Combobox;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APP接入应用信息
 */
@Entity
@Table(name = "dk_app")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Getter
@Setter
public class App extends  BaseEntity   {

    /**
     * 主键
     */
    @Id
    @Column(name = "appid",length = 32,unique = true,nullable = false)
    private String appID;

    /**
     * 名称
     */
    @Column(length = 50,name="appname" ,nullable = false )
    private String name;


    /**
     * 加密盐值
     */
    @Column(length = 8,name="salt",columnDefinition = "char(8)  COMMENT '加密盐值'" ,nullable = false )
    private String salt;

    /**
     * 密钥
     */
    @Column( length = 24,nullable = false )
    private String appkey;


    /**
     * 当前接入应用的 默认 异步回调地址
     */
    @Column(name="default_notify_url",length = 500,nullable = false )
    private String defaultNotifyUrl;

    /**
     * 当前接入应用的 默认 同步 通知地址
     */
    @Column(name="default_return_url",length = 500,nullable = false )
    private String defaultReturnUrl;


    /**
     * 当前应用 支付 默认的商品名称
     */
    @Column(name="default_productname",length = 40,nullable = false )
    private String defaultProductname;


    /**
     * 订单 默认 有效时间（分钟）
     */
    @Column( name="default_order_exprise_time" , nullable = false )
    private int defaultOrderExpriseTime;

    /**
     * 状态
     */
    @Column(  nullable = false )
    private Byte state;

    /**
     * 备注
     */
    @Column(length = 500   )
    private String remark;

    /**
     * 应用 接入 到期日期
     */
    @Column(  name="expire_date" ,nullable = false  )
    @JsonFormat(timezone = "GMT+8", pattern = "YYYY-MM-dd HH:mm")
    private Date expireDate;


    @PrePersist
    void preSave(){
        if(StringUtils.isEmpty(appID)){
            appID = RandomStringUtils.random(24,true,true);
        }
    }


    public App() {

    }

    public enum StateEnum {

        /**
         *  启用中
         */
        Enable((byte)1,"启用中"),
        /**
         *  已禁用
         */
        Disable((byte)-1 ,"已禁用"),
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
        public static StateEnum getNameByCode(Byte code) {
            if(null == code){
                return  null;
            }
            for (StateEnum en : StateEnum.values()) {
                if ( en.getCode() == code ) {
                    return en;
                }
            }
            return null;
        }

        public static List<Combobox> getEnumList(){

            List<Combobox> list = new ArrayList<Combobox>();

            Combobox boxNone = new Combobox();
            boxNone.setCode("");
            boxNone.setName( "--请选择--");
            list.add( boxNone );

            for(StateEnum type:StateEnum.values()){
                Combobox box = new Combobox();
                box.setCode(type.getCode()+"");
                box.setName(type.getName());

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


}
